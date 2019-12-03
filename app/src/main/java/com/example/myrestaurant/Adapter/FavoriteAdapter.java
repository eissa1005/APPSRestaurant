package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Interface.IOnRecyclerViewClickListener;
import com.example.myrestaurant.Model.EventBus.FoodDetailEvent;
import com.example.myrestaurant.Model.Response.Favorite;
import com.example.myrestaurant.Model.Response.Restaurant;
import com.example.myrestaurant.R;
import com.example.myrestaurant.UI.FoodDetailActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private Context mContext;
    private List<Favorite> mFavoriteList;
    private CompositeDisposable mCompositeDisposable;

    public FavoriteAdapter(Context mContext, List<Favorite> mFavoriteList) {
        this.mContext = mContext;
        this.mFavoriteList = mFavoriteList;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_favorite_item, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        Picasso.get().load(mFavoriteList.get(position).getFoodImage()).into(holder.img_food);
        holder.txt_food_name.setText(mFavoriteList.get(position).getFoodName());
        holder.txt_food_price.setText(new StringBuilder(mContext.getString(R.string.money_sign))
                .append(mFavoriteList.get(position).getPrice()));
        holder.txt_restaurantName.setText(mFavoriteList.get(position).getRestaurantName());

        // Event
        holder.setListener((view, i) -> {
            mCompositeDisposable.add(APIManage.getApi().getFoodById(Common.API_KEY, mFavoriteList.get(i).getFoodId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(foodModel -> {

                        if (foodModel.isSuccess()) {
                            // When user click to favorite item, just start FoodDetailActivity
                            mContext.startActivity(new Intent(mContext, FoodDetailActivity.class));
                            if (Common.currentRestaurant == null) {
                                Common.currentRestaurant = new Restaurant();
                            }
                            Common.currentRestaurant.setRestaurantId(mFavoriteList.get(i).getRestaurantId());
                            Common.currentRestaurant.setRestaurantName(mFavoriteList.get(i).getRestaurantName());
                            EventBus.getDefault().postSticky(new FoodDetailEvent(true, foodModel.getResult().get(0)));

                        }
                        else {
                            Toast.makeText(mContext, "[GET FOOD BY RESULT]"+foodModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Toast.makeText(mContext, "[GET FOOD BY ID]", Toast.LENGTH_SHORT).show();
                    }));
        });
    }

    @Override
    public int getItemCount() {
       if(mFavoriteList==null)return 0;
       else
        return  mFavoriteList.size();
    }


    public class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_food)
        ImageView img_food;
        @BindView(R.id.txt_food_name)
        TextView txt_food_name;
        @BindView(R.id.txt_food_price)
        TextView txt_food_price;
        @BindView(R.id.txt_restaurantName)
        TextView txt_restaurantName;

        Unbinder mUnbinder;
        IOnRecyclerViewClickListener onRecyclerViewClickListener;

        public void setListener(IOnRecyclerViewClickListener onRecyclerViewClickListener) {
            this.onRecyclerViewClickListener = onRecyclerViewClickListener;
        }

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            mUnbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerViewClickListener.onClick(v, getAdapterPosition());
        }
    }

}

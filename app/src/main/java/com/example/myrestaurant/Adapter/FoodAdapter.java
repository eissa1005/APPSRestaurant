package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Interface.IFoodDetailOrCartClickListener;
import com.example.myrestaurant.Interface.IOnRecyclerViewClickListener;
import com.example.myrestaurant.Model.DAO.CartDAO;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.DAO.CartDatabase;
import com.example.myrestaurant.Model.DAO.CartItem;
import com.example.myrestaurant.Model.DAO.LocalCartDataSource;
import com.example.myrestaurant.Model.EventBus.FoodDetailEvent;
import com.example.myrestaurant.Model.Response.Foods;
import com.example.myrestaurant.R;
import com.example.myrestaurant.UI.FoodDetailActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.ParsePosition;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    Context mContext;
    List<Foods> listFoods;
    private CompositeDisposable mCompositeDisposable;
    private CartDataSource mCartDataSource;

    public FoodAdapter(Context context, List<Foods> listFoods) {
        this.mContext = context;
        this.listFoods = listFoods;
        mCompositeDisposable = new CompositeDisposable();
        mCartDataSource = new LocalCartDataSource(CartDatabase.getInstance(mContext).cartDAO());
    }

    public void onStop() {
        mCompositeDisposable.clear();
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        Foods foods = listFoods.get(position);
        Picasso.get().load(foods.getImage())
                .placeholder(R.drawable.app_icon).into(holder.img_food);
        holder.txt_food_name.setText(foods.getName());

        holder.txt_food_price.setText(new StringBuilder(mContext.getString(R.string.money_sign))
                .append(foods.getPrice()));
        holder.setOnFoodDetailOrCartClickListener((view, i, isDetail) -> {
            // Create CartItem
            if(isDetail){
                // EventBus Send Data To FoodDetailActivity
                EventBus.getDefault().postSticky(new FoodDetailEvent(true,listFoods.get(i)));
                mContext.startActivity(new Intent(mContext, FoodDetailActivity.class));

            }else{
                CartItem cartItem = new CartItem();
                cartItem.setFoodId(listFoods.get(i).getId());
                cartItem.setFoodName(listFoods.get(i).getName());
                cartItem.setFoodPrice(listFoods.get(i).getPrice());
                cartItem.setFoodImage(listFoods.get(i).getImage());
                cartItem.setFoodQuantity(1);
                cartItem.setUserPhone(Common.currentUser.getUserPhone());
                cartItem.setRestaurantId(Common.currentRestaurant.getRestaurantId());
                cartItem.setFoodAddon("NORMAL");
                cartItem.setFoodSize("NORMAL");
                cartItem.setFoodExtraPrice(0.0);
                cartItem.setFbid(Common.currentUser.getfBID());
                mCompositeDisposable.add(mCartDataSource.insertOrReplaceAll(cartItem)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Toast.makeText(mContext, "Add To Cart "+cartItem.getFoodName(), Toast.LENGTH_SHORT).show();
                            Log.e("cartItem", cartItem.getFbid());
                        }, throwable -> {
                            Toast.makeText(mContext, "AddFailure " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
            }


        });


    }

    @Override
    public int getItemCount() {
        if (listFoods == null) return 0;
        else
            return listFoods.size();
    }

    public class FoodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_food)
        ImageView img_food;
        @BindView(R.id.txt_food_name)
        TextView txt_food_name;
        @BindView(R.id.txt_food_price)
        TextView txt_food_price;
        @BindView(R.id.img_detail)
        ImageView img_detail;
        @BindView(R.id.img_fav)
        ImageView img_fav;
        @BindView(R.id.img_cart)
        ImageView img_cart;

        IFoodDetailOrCartClickListener onFoodDetailOrCartClickListener;

        public void setOnFoodDetailOrCartClickListener(IFoodDetailOrCartClickListener onFoodDetailOrCartClickListener) {
            this.onFoodDetailOrCartClickListener = onFoodDetailOrCartClickListener;
        }

        Unbinder unbinder;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            img_detail.setOnClickListener(this);
            img_cart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_detail:
                    onFoodDetailOrCartClickListener.onFoodItemClickListener(v, getAdapterPosition(), true);
                    break;
                case R.id.img_cart:
                    onFoodDetailOrCartClickListener.onFoodItemClickListener(v, getAdapterPosition(), false);
                    break;
            }
        }

    }
}

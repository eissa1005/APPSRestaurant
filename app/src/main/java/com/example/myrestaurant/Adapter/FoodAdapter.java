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

import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Interface.IFoodDetailOrCartClickListener;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.DAO.CartDatabase;
import com.example.myrestaurant.Model.DAO.CartItem;
import com.example.myrestaurant.Model.DAO.LocalCartDataSource;
import com.example.myrestaurant.Model.EventBus.FoodDetailEvent;
import com.example.myrestaurant.Model.Response.Favorite;
import com.example.myrestaurant.Model.Response.FavoriteOnlyId;
import com.example.myrestaurant.Model.Response.Foods;
import com.example.myrestaurant.R;
import com.example.myrestaurant.UI.FoodDetailActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    Context mContext;
    List<Foods> listFoods;
    private CompositeDisposable compositeDisposable;
    private CartDataSource mCartDataSource;
    private Map<String, Favorite> favoriteMap;

    public FoodAdapter(Context context, List<Foods> listFoods) {
        this.mContext = context;
        this.listFoods = listFoods;
        compositeDisposable = new CompositeDisposable();
        favoriteMap = new HashMap<>();
        mCartDataSource = new LocalCartDataSource(CartDatabase.getInstance(mContext).cartDAO());
    }

    public void onStop() {
        compositeDisposable.clear();
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_food, parent, false);
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

        if (Common.currentFavOfRestaurant != null && Common.currentFavOfRestaurant.size() > 0) {
            if (Common.checkFavorite(Common.currentFavOfRestaurant.get(position).getFoodId())) {
                holder.img_fav.setImageResource(R.drawable.ic_favorite_button_color_24dp);
                holder.img_fav.setTag(true);
            } else {
                holder.img_fav.setImageResource(R.drawable.ic_favorite_border_button_color_24dp);
                holder.img_fav.setTag(false);
            }
        } else {
            // Default, all item is no favorite
            holder.img_fav.setTag(false);
        }
        // Event
        // Favorite Event Img_fav ClickListene
        holder.img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView fav = ((ImageView) v);
                if (((boolean) fav.getTag()))
                {
                    compositeDisposable.add(APIManage.getApi().removeFavorite(Common.API_KEY,
                            Common.currentUser.getFBID(),
                            listFoods.get(position).getFoodID(),
                            Common.currentRestaurant.getRestaurantId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(favoriteModel -> {
                                fav.setImageResource(R.drawable.ic_favorite_border_button_color_24dp);
                                fav.setTag(false);
                                if (Common.currentFavOfRestaurant != null) {
                                    Common.removeFavorite(listFoods.get(position).getFoodID());
                                }
                            }, throwable -> {
                                Toast.makeText(mContext, "[ Remove Favorite]" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }));
                }
                else {
                    // Add Favorite by HashMap
                    try {
                        Favorite favorite = new Favorite();
                        favorite.setFBID(Common.currentUser.getFBID());
                        favorite.setFoodId(listFoods.get(position).getFoodID());
                        favorite.setRestaurantId(Common.currentRestaurant.getRestaurantId());
                        favorite.setRestaurantName(Common.currentRestaurant.getRestaurantName());
                        favorite.setFoodName(listFoods.get(position).getName());
                        favorite.setFoodImage(listFoods.get(position).getImage());
                        favorite.setPrice(listFoods.get(position).getPrice());
                        favoriteMap.put("favorite", favorite);
                        Log.d("favoriteMap", favorite.getFoodName());

                        compositeDisposable.add(APIManage.getApi()
                                .AddFavorite(Common.API_KEY, favoriteMap)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(favoriteModel -> {
                                    fav.setImageResource(R.drawable.ic_favorite_button_color_24dp);
                                    fav.setTag(true);
                                    Log.d("favoriteModel", "Called");
                                    if (Common.currentFavOfRestaurant != null) {
                                        Common.currentFavOfRestaurant.add(new FavoriteOnlyId(listFoods.get(position).getFoodID()));
                                    }
                                }, throwable -> {
                                    Toast.makeText(mContext, " [ Add Favorite ] " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }));
                    }
                    catch(Exception ex){
                        Log.e("FavoriteException",ex.getMessage());
                    }
                }
            }
        });


        holder.setOnFoodDetailOrCartClickListener((view, i, isDetail) -> {
            // Create CartItem
            if (isDetail) {
                // EventBus Send Data To FoodDetailActivity
                EventBus.getDefault().postSticky(new FoodDetailEvent(true, listFoods.get(i)));
                mContext.startActivity(new Intent(mContext, FoodDetailActivity.class));

            } else {
                CartItem cartItem = new CartItem();
                cartItem.setFoodId(listFoods.get(i).getFoodID());
                cartItem.setFoodName(listFoods.get(i).getName());
                cartItem.setFoodPrice(listFoods.get(i).getPrice());
                cartItem.setFoodImage(listFoods.get(i).getImage());
                cartItem.setFoodQuantity(1);
                cartItem.setUserPhone(Common.currentUser.getUserPhone());
                cartItem.setRestaurantId(Common.currentRestaurant.getRestaurantId());
                cartItem.setFoodAddon("NORMAL");
                cartItem.setFoodSize("NORMAL");
                cartItem.setFoodExtraPrice(0.0);
                cartItem.setFBID(Common.currentUser.getFBID());
                compositeDisposable.add(mCartDataSource.insertOrReplaceAll(cartItem)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Toast.makeText(mContext, "Add To Cart " + cartItem.getFoodName(), Toast.LENGTH_SHORT).show();
                            //  Log.e("cartItem", cartItem.getFbid());
                        }, throwable -> {
                            Toast.makeText(mContext, "AddFailure " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));
            }


        });


    }

    public void changeData(List<Foods> newListFoods) {
        listFoods.clear();
        listFoods.addAll(newListFoods);
        notifyDataSetChanged();
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
            img_fav.setOnClickListener(this);
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

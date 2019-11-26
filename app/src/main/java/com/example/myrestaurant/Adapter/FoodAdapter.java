package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.Interface.IFoodDetailOrCartClickListener;
import com.example.myrestaurant.Interface.IOnRecyclerViewClickListener;
import com.example.myrestaurant.Model.Response.Foods;
import com.example.myrestaurant.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    List<Foods> listFoods;
    Context mContext;

    public FoodAdapter(Context context, List<Foods> listFoods) {
        this.mContext = context;
        this.listFoods = listFoods;
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

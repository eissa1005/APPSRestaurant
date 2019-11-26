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

import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Interface.IOnRecyclerViewClickListener;
import com.example.myrestaurant.Model.EventBus.MenuItemEvent;
import com.example.myrestaurant.Model.Response.Restaurant;
import com.example.myrestaurant.R;
import com.example.myrestaurant.UI.MenuActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {
     private Context mContext;
     private List<Restaurant> mRestaurantList;

     public RestaurantAdapter(Context context,List<Restaurant> restaurantList){
         this.mContext = context;
         this.mRestaurantList = restaurantList;
     }
    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_restaurant, parent, false);
         return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        Picasso.get().load(mRestaurantList.get(position).getImage()).into(holder.img_restaurant);
        holder.txt_restaurant_name.setText(new StringBuilder(mRestaurantList.get(position).getRestaurantName()));
        holder.txt_restaurant_address.setText(new StringBuilder(mRestaurantList.get(position).getRestaurantAddress()));

         holder.setOnRecyclerViewClickListener((view,i)->{
           Common.currentRestaurant=mRestaurantList.get(i);
           // Here use postSticky, that mean this event will be listen from other activity
          // It will different with just 'post'
            EventBus.getDefault().postSticky(new MenuItemEvent(true,mRestaurantList.get(i)));
           mContext.startActivity(new Intent(mContext, MenuActivity.class));

         });

    }

    @Override
    public int getItemCount() {
        if(mRestaurantList == null) return 0;
        else
          return mRestaurantList.size();
    }

     class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         @BindView(R.id.img_restaurant)
        ImageView img_restaurant;
        @BindView(R.id.txt_restaurant_name)
        TextView txt_restaurant_name;
        @BindView(R.id.txt_restaurant_address)
        TextView txt_restaurant_address;

         IOnRecyclerViewClickListener onRecyclerViewClickListener;
         public void setOnRecyclerViewClickListener(IOnRecyclerViewClickListener onRecyclerViewClickListener) {
             this.onRecyclerViewClickListener = onRecyclerViewClickListener;
         }
        Unbinder unbinder;
        public RestaurantHolder(@NonNull View itemView) {
            super(itemView);
            unbinder =ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View v) {
             onRecyclerViewClickListener.onClick(v,getAdapterPosition());
         }
     }
}

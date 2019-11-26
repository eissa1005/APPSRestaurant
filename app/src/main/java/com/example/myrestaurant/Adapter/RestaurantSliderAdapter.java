package com.example.myrestaurant.Adapter;

import com.example.myrestaurant.Model.Response.Restaurant;
import java.util.List;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class RestaurantSliderAdapter extends SliderAdapter {

   private List<Restaurant> mRestaurantList;
    public RestaurantSliderAdapter(List<Restaurant> mRestaurantList){
        this.mRestaurantList = mRestaurantList;
    }

    @Override
    public int getItemCount() {
       if(mRestaurantList == null)  return 0;
       else
           return  mRestaurantList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
       imageSlideViewHolder.bindImageSlide(mRestaurantList.get(position).getImage());
    }
}

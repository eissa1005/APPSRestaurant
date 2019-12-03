package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.Model.DAO.CartItem;

import java.util.List;

import butterknife.Unbinder;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    Context mContext;
    List<CartItem> cartItemList;

    public CartAdapter(Context mContext, List<CartItem> cartItemList) {
        this.mContext = mContext;
        this.cartItemList = cartItemList;
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(cartItemList == null)return  0;
        else
            return  cartItemList.size();
    }

    Unbinder unbinder;
    public class CartHolder extends RecyclerView.ViewHolder {
        public CartHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

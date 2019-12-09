package com.example.myrestaurant.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.Response.Orders;
import com.example.myrestaurant.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrdersHolder> {

    private Context mContext;
    public List<Orders> mOrderList;
    private SimpleDateFormat mSimpleDateFormat;
    @SuppressLint("SimpleDateFormat")
    public OrderAdapter(Context mContext, List<Orders> mOrderList) {
        this.mContext = mContext;
        this.mOrderList = mOrderList;
        mSimpleDateFormat =new SimpleDateFormat("dd/MM/yyyy");
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order, parent, false);
       return  new OrdersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHolder holder, int position) {

        holder.txt_num_of_item.setText(new StringBuilder("Num of Items: ").append(mOrderList.get(position).getNumOfItem()));
        holder.txt_order_address.setText(new StringBuilder(mOrderList.get(position).getOrderAddress()));
        holder.txt_order_number.setText(new StringBuilder("Orders Number : #").append(String.valueOf(mOrderList.get(position).getOrderID())));
        holder.txt_order_date.setText(new StringBuilder("Date Order ").append(String.valueOf(mOrderList.get(position).getOrderDate())));
        holder.txt_order_phone.setText(new StringBuilder(mOrderList.get(position).getOrderPhone()));
        holder.txt_order_status.setText(Common.convertStatusToString(mOrderList.get(position).getOrderStatus()));
        holder.txt_order_total_price.setText(new StringBuilder(mContext.getString(R.string.money_sign)).append(String.valueOf(mOrderList.get(position).getTotalPrice())));

        if (mOrderList.get(position).isCode()) {
            holder.txt_payment_method.setText(new StringBuilder("Cash On Delivery"));
        } else {
            holder.txt_payment_method.setText(new StringBuilder("TransID: ").append(mOrderList.get(position).getTransactionId()));
        }
    }

    @Override
    public int getItemCount() {
        if(mOrderList == null)return  0;
        else
       return mOrderList.size();
    }

    public class OrdersHolder extends ViewHolder{
        @BindView(R.id.txt_order_number)
        TextView txt_order_number;
        @BindView(R.id.txt_order_status)
        TextView txt_order_status;
        @BindView(R.id.txt_order_phone)
        TextView txt_order_phone;
        @BindView(R.id.txt_order_address)
        TextView txt_order_address;
        @BindView(R.id.txt_order_date)
        TextView txt_order_date;
        @BindView(R.id.txt_order_total_price)
        TextView txt_order_total_price;
        @BindView(R.id.txt_num_of_item)
        TextView txt_num_of_item;
        @BindView(R.id.txt_payment_method)
        TextView txt_payment_method;

        Unbinder mUnbinder;
        public OrdersHolder(@NonNull View itemView) {
            super(itemView);
            mUnbinder=ButterKnife.bind(this,itemView);
        }
    }
}

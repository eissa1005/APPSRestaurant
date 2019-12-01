package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Interface.IOnRecyclerViewClickListener;
import com.example.myrestaurant.Model.EventBus.FoodListEvent;
import com.example.myrestaurant.Model.Response.Category;
import com.example.myrestaurant.R;
import com.example.myrestaurant.UI.FoodListActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    Context mContext;
    List<Category> mCategoryList;

    public CategoryAdapter(Context mContext, List<Category> mCategoryList) {
        this.mContext = mContext;
        this.mCategoryList = mCategoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_category, parent, false);
        return new CategoryHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = mCategoryList.get(position);
        Picasso.get().load(category.getImage()).into(holder.img_category);
        holder.txt_category.setText(category.getName());
        holder.setOnRecyclerViewClickListener((view, i) -> {
            int getMenuId = mCategoryList.get(i).getMenuId();
            Log.e("CategoryMenuId", "MenuId is " + getMenuId);
            // Send sticky post event to FoodListActivity
            EventBus.getDefault().postSticky(new FoodListEvent(true, mCategoryList.get(i)));
            mContext.startActivity(new Intent(mContext, FoodListActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        if (mCategoryList == null) return 0;
        return mCategoryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mCategoryList.size() == 1) {
            return Common.DEFAULT_COLUMN_COUNT;
        } else {
            if (mCategoryList.size() % 2 == 0) {
                return Common.DEFAULT_COLUMN_COUNT;
            } else {
                return (position > 1 && position == mCategoryList.size() - 1)
                        ? Common.FULL_WIDTH_COLUMN
                        : Common.DEFAULT_COLUMN_COUNT;
            }

        }
    }


    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_category)
        ImageView img_category;
        @BindView(R.id.txt_category)
        TextView txt_category;

        IOnRecyclerViewClickListener onRecyclerViewClickListener;

        public void setOnRecyclerViewClickListener(IOnRecyclerViewClickListener onRecyclerViewClickListener) {
            this.onRecyclerViewClickListener = onRecyclerViewClickListener;
        }


        Unbinder unbinder;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerViewClickListener.onClick(v, getAdapterPosition());
        }
    }
}

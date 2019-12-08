package com.example.myrestaurant.UI;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.Adapter.OrderAdapter;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Model.Response.Orders;
import com.example.myrestaurant.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;

public class ViewOrderActivity extends BaseActivity {
    private static final String TAG = ViewOrderActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_order)
    RecyclerView recycler_view_order;


    private CompositeDisposable compositeDisposable;
    private AlertDialog mDialog;

    private OrderAdapter mAdapter;
    private List<Orders> mOrderList;
    private int maxData = 0;

    private LayoutAnimationController mLayoutAnimationController;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Log.d(TAG, "onCreate: started!!");

        init();
        initView();

        //getAllOrder();
        getMaxOrder();
    }

    private void getMaxOrder() {

    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);
        toolbar.setTitle(getString(R.string.your_order));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mLayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_item_from_left);


    }

    private void init() {
        Log.d(TAG, "init: called!!");
        compositeDisposable = new CompositeDisposable();
        mDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }

}

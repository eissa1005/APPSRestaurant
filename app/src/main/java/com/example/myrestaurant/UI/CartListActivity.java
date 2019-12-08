package com.example.myrestaurant.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.braintreepayments.api.dropin.BaseActivity;
import com.example.myrestaurant.Adapter.CartAdapter;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.DAO.CartDatabase;
import com.example.myrestaurant.Model.DAO.LocalCartDataSource;
import com.example.myrestaurant.Model.EventBus.CalculatePriceEvent;
import com.example.myrestaurant.Model.EventBus.SendTotalCashEvent;
import com.example.myrestaurant.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartListActivity extends BaseActivity {

    private static final String TAG = CartListActivity.class.getSimpleName();
    @BindView(R.id.toolbar_catList)
    Toolbar toolbar;
    @BindView(R.id.recycler_cart)
    RecyclerView recycler_cart;
    @BindView(R.id.txt_final_price)
    TextView txt_final_price;
    @BindView(R.id.btn_order)
    Button btn_order;
    CartAdapter adapter;

    private CompositeDisposable compositeDisposable;
    private CartDataSource mCartDataSource;

    private LayoutAnimationController mLayoutAnimationController;


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started!!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        init();
        initView();
        getAllItemInCart();

    }

    private void getAllItemInCart() {
        Log.d(TAG, "getAllItemInCart: called!!");
        compositeDisposable.add(mCartDataSource.getAllCart(Common.currentUser.getFBID(),
                Common.currentRestaurant.getRestaurantId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItems -> {

                    if (cartItems.isEmpty()) {
                        btn_order.setText(getString(R.string.empty_cart));
                        btn_order.setEnabled(false);
                        btn_order.setBackgroundResource(android.R.color.darker_gray);
                        adapter=new CartAdapter(CartListActivity.this,null);
                        recycler_cart.setAdapter(adapter);
                        txt_final_price.setText("0");
                    }
                    else {
                        btn_order.setText(getString(R.string.place_order));
                        btn_order.setEnabled(true);
                        btn_order.setBackgroundResource(R.color.colorPrimary);

                        adapter = new CartAdapter(CartListActivity.this, cartItems);
                        recycler_cart.setAdapter(adapter);
                        recycler_cart.setLayoutAnimation(mLayoutAnimationController);

                        calculateCartTotalPrice();
                    }



                }, throwable -> {
                    Toast.makeText(this, "[GET CART]"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void calculateCartTotalPrice() {
        Log.d(TAG, "calculateCartTotalPrice: called!!");
        mCartDataSource.sumPrice(Common.currentUser.getFBID(), Common.currentRestaurant.getRestaurantId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        if (aLong == 0) {
                            btn_order.setText(getString(R.string.empty_cart));
                            btn_order.setEnabled(false);
                            txt_final_price.setText("0");
                            btn_order.setBackgroundResource(android.R.color.darker_gray);
                        } else {
                            btn_order.setText(getString(R.string.place_order));
                            btn_order.setEnabled(true);
                            btn_order.setBackgroundResource(R.color.colorPrimary);
                        }

                        txt_final_price.setText(String.valueOf(aLong));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("Query returned empty"))
                            txt_final_price.setText("0");
                        else
                            Toast.makeText(CartListActivity.this, "[SUM CART]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);
        mLayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_item_from_left);

        toolbar.setTitle(getString(R.string.cart));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycler_cart.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(layoutManager);
        recycler_cart.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        btn_order.setOnClickListener(v -> {
            EventBus.getDefault().postSticky(new SendTotalCashEvent(txt_final_price.getText().toString()));
            startActivity(new Intent(CartListActivity.this, PlaceOrderActivity.class));
        });
    }

    private void init() {
        compositeDisposable = new CompositeDisposable();
        Log.d(TAG, "init: called!!");
        mCartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
    }

    // Event Bus

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void calculatePrice(CalculatePriceEvent event) {
        if (event != null) {

            calculateCartTotalPrice();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

package com.example.myrestaurant.UI;

import android.os.Bundle;

import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Adapter.RestaurantAdapter;
import com.example.myrestaurant.Adapter.RestaurantSliderAdapter;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.EventBus.RestaurantLoadEvent;
import com.example.myrestaurant.Model.Response.Restaurant;
import com.example.myrestaurant.R;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.myrestaurant.Service.PicassoImageLoadingService;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private TextView txt_user_name;
    private TextView txt_user_phone;

    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_restaurant)
    RecyclerView recycler_restaurant;
    RestaurantAdapter adapter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private android.app.AlertDialog mDialog;

    private LayoutAnimationController mLayoutAnimationController;

    @Override
    protected void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started!!");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        txt_user_name = headerView.findViewById(R.id.headerName);
        txt_user_phone = headerView.findViewById(R.id.headerPhone);

        txt_user_name.setText(Common.currentUser.getUserName());
        txt_user_phone.setText(Common.currentUser.getUserPhone());

        init();
        initView();

        loadRestaurant();

    }


    private void loadRestaurant() {
        Log.d(TAG, "loadRestaurant: called!!");
        mDialog.show();
        mCompositeDisposable.add(APIManage.getApi().getRestaurant(Common.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restauranrModel -> {
                    if (restauranrModel.getSuccess()) {
                        // Here, we ill user EventBus to send local event set adapter and slider
                        EventBus.getDefault().post(new RestaurantLoadEvent(true, restauranrModel.getResult()));
                        Log.e("RestauranrModel", "" + restauranrModel.getSuccess());
                        mDialog.dismiss();
                    }
                    mDialog.dismiss();
                }, throwable -> {
                    EventBus.getDefault().post(new RestaurantLoadEvent(false, throwable.getMessage()));
                    Toast.makeText(this, "[GET RESTAURANT]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("throwableRestauranr", "" + throwable.getMessage());

                }));
    }

    private void displayRestaurant(List<Restaurant> restaurantList) {
        Log.d(TAG, "displayRestaurant: called!!");
        adapter = new RestaurantAdapter(this, restaurantList);
        recycler_restaurant.setAdapter(adapter);
        recycler_restaurant.setLayoutAnimation(mLayoutAnimationController);
    }

    private void displayBanner(List<Restaurant> restaurantList) {
        Log.d(TAG, "displayBanner: called!!");
        Log.d(TAG, "displayBanner: size: " + restaurantList.size());
        banner_slider.setAdapter(new RestaurantSliderAdapter(restaurantList));
    }

    // Listen EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processRestaurantLoadEvent(RestaurantLoadEvent event) {
        Log.d(TAG, "processRestaurantLoadEvent: called!!");
        if (event.isSuccess()) {
            displayBanner(event.getRestaurantList());
            int size = event.getRestaurantList().size();
            Log.e("Listsize", "Size of Restaurnt Banner : " + size);
            displayRestaurant(event.getRestaurantList());
            int sizeListRestaurant = event.getRestaurantList().size();
            Log.e("sizeListRestaurant", "Size of Restaurnt Banner : " + sizeListRestaurant);
        } else {
            Toast.makeText(this, "[RESTAURANT LOAD]" + event.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Message", event.getMessage());
        }

        mDialog.dismiss();
    }

    private void init() {
        Log.d(TAG, "init: called!!");
        mDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        Slider.init(new PicassoImageLoadingService());
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_restaurant.setLayoutManager(layoutManager);
        recycler_restaurant.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        mLayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_item_from_left);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log_out) {
            //signOut();
        } else if (id == R.id.nav_nearby) {
            //startActivity(new Intent(HomeActivity.this, NearbyRestaurantActivity.class));
        } else if (id == R.id.nav_order_history) {
            // startActivity(new Intent(HomeActivity.this, ViewOrderActivity.class));
        } else if (id == R.id.nav_update_info) {
            // startActivity(new Intent(HomeActivity.this, UpdateInfoActivity.class));
        } else if (id == R.id.nav_fav) {
            //startActivity(new Intent(HomeActivity.this, FavoriteActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
}

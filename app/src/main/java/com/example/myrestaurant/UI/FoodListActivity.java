package com.example.myrestaurant.UI;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Adapter.FoodAdapter;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.EventBus.FoodListEvent;
import com.example.myrestaurant.Model.Response.Category;
import com.example.myrestaurant.Model.Response.Foods;
import com.example.myrestaurant.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FoodListActivity extends BaseActivity {
    private static final String TAG = "FoodListActivity";
    @BindView(R.id.img_category)
    KenBurnsView img_category;
    @BindView(R.id.recycler_food_list)
    RecyclerView recycler_foodList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    AlertDialog mDialog;
    CompositeDisposable compositeDisposable;
    private FoodAdapter adapter;
    private FoodAdapter searchAdapter;
    private Category selectedCategory;
    private LayoutAnimationController layoutAnimationController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        Log.e("FoodList", "onCreate: started!!");
        init();
        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(activity);
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_item_from_left);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_foodList.setLayoutManager(layoutManager);
        recycler_foodList.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    private void init() {
        if (mDialog == null) {
            mDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).build();
            compositeDisposable = new CompositeDisposable();
        }
    }

    private void displayFoodslist(List<Foods> foodsList) {
        Log.d(TAG, "displayFoodslist: called!!");
        adapter = new FoodAdapter(activity, foodsList);
        recycler_foodList.setAdapter(adapter);
        recycler_foodList.setLayoutAnimation(layoutAnimationController);
    }

    // Listen Data
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadFoodslist(@NotNull FoodListEvent event) {
        Log.d("loadFoodslist","Called");
        if (event.isSuccess()) {
            Picasso.get().load(event.getCategory().getImage()).into(img_category);
            toolbar.setTitle(event.getCategory().getName());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDialog.show();
            compositeDisposable.add(APIManage.getApi()
                    .getFoods(Common.API_KEY, event.getCategory().getMenuId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(foodsModel -> {
                        if(foodsModel.isSuccess()) {
                            double size = foodsModel.getResult().size();
                            Log.d("FoodListSize", "FoodList size : " + size);
                            displayFoodslist(foodsModel.getResult());
                        }else {
                            Toast.makeText(this, "[GET FOOD RESULT]" + foodsModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        mDialog.dismiss();
                    }, throwable -> {
                        Toast.makeText(activity, "FoodListExption" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        }
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

    @Override
    protected void onDestroy() {
        if(adapter != null){
            adapter.onStop();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

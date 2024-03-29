package com.example.myrestaurant.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Adapter.CategoryAdapter;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.DAO.CartDatabase;
import com.example.myrestaurant.Model.DAO.LocalCartDataSource;
import com.example.myrestaurant.Model.EventBus.MenuItemEvent;
import com.example.myrestaurant.R;
import com.example.myrestaurant.Utility.SpaceItemDecoration;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MenuActivity extends BaseActivity {
    private static final String TAG = MenuActivity.class.getSimpleName();
    @BindView(R.id.img_menu)
    KenBurnsView img_restaurant;
    @BindView(R.id.recycler_category)
    RecyclerView recycler_category;
    @BindView(R.id.toolbar_menu)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton btn_cart;
    @BindView(R.id.badge)
    NotificationBadge badge;
    CompositeDisposable mCompositeDisposable;
    AlertDialog mDialog;
    CategoryAdapter adapter;
    CartDataSource cartDataSource;

    private LayoutAnimationController mLayoutAnimationController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
        initView();
        countCartByRestaurant();

    }


    private void countCartByRestaurant() {
        Log.e("countCart","Called");
        cartDataSource.countItemInCart(Common.currentUser.getFBID(), Common.currentRestaurant.getRestaurantId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        badge.setText(String.valueOf(integer));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(activity, "[Cart Count]" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                       Log.e("BadegError",e.getMessage());
                    }
                });
    }


    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(activity);
        mLayoutAnimationController = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_item_from_left);
        btn_cart.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, CartListActivity.class));
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null) {
                    switch (adapter.getItemViewType(position)) {
                        case Common.DEFAULT_COLUMN_COUNT:
                            return 1;
                        case Common.FULL_WIDTH_COLUMN:
                            return 2;
                        default:
                            return -1;
                    }
                } else {
                    return -1;
                }
            }
        });
        recycler_category.setLayoutManager(layoutManager);
        recycler_category.addItemDecoration(new SpaceItemDecoration(8));
    }

    private void init() {
        mCompositeDisposable = new CompositeDisposable();
        mDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartByRestaurant();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadMenuByRestaurant(MenuItemEvent event) {
        if (event.isSuccess()) {
            Picasso.get().load(event.getRestaurant().getImage()).into(img_restaurant);
            toolbar.setTitle(event.getRestaurant().getRestaurantName());
            Log.e("event", "RestaurantName Banner : " + event.getRestaurant().getRestaurantName());

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Log.e("RestaurantId", "RestaurantId is : " + event.getRestaurant().getRestaurantId());

            mCompositeDisposable.add(APIManage.getApi()
                    .getCategories(Common.API_KEY, event.getRestaurant().getRestaurantId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(menuModel -> {
                        Log.d("menuModelCalled","Called");
                        if(menuModel.isSuccess()) {
                            adapter = new CategoryAdapter(MenuActivity.this, menuModel.getResult());
                            recycler_category.setAdapter(adapter);
                            recycler_category.setLayoutAnimation(mLayoutAnimationController);
                            Log.e("menuModel", "menuModel size is : " + menuModel.getResult().size());
                        }
                    }, throwable -> {
                        mDialog.dismiss();
                        Toast.makeText(activity, "GetMenuError " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("menuModelMessage", throwable.getMessage());
                    })

            );
        }
    }
}

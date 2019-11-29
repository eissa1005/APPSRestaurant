package com.example.myrestaurant.UI;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Adapter.AddonAdapter;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.DAO.CartDatabase;
import com.example.myrestaurant.Model.DAO.LocalCartDataSource;
import com.example.myrestaurant.Model.EventBus.AddOnEventChange;
import com.example.myrestaurant.Model.EventBus.AddonLoadEvent;
import com.example.myrestaurant.Model.EventBus.FoodDetailEvent;
import com.example.myrestaurant.Model.EventBus.SizeLoadEvent;
import com.example.myrestaurant.Model.Response.Foods;
import com.example.myrestaurant.Model.Response.Size;
import com.example.myrestaurant.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FoodDetailActivity extends BaseActivity {
    static final String TAG = FoodDetailActivity.class.getSimpleName();
    @BindView(R.id.fab_add_to_cart)
    FloatingActionButton fab_add_to_cart;
    @BindView(R.id.btn_view_cart)
    Button btn_view_cart;
    @BindView(R.id.txt_money)
    TextView txt_money;
    @BindView(R.id.radio_group_size)
    RadioGroup radio_group_size;
    @BindView(R.id.recycler_addon)
    RecyclerView recycler_addon;
    @BindView(R.id.txt_description)
    TextView txt_description;
    @BindView(R.id.img_food_detail)
    KenBurnsView img_food_detail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    LayoutAnimationController layoutAnimationController;
    CompositeDisposable compositeDisposable;
    AlertDialog mDialog;
    CartDataSource cartDataSource;
    Foods selectFood;
    private Double originalPrice;
    private double sizePrice = 0.0;
    private String sizeSelected;
    private double addOnPrice = 0.0;
    private double extraPrice;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        init();
        initView();
    }

    private void initView() {
        Log.d("initView", "Called");
        ButterKnife.bind(activity);
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_item_from_left);
    }

    private void init() {
        mDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).build();
        compositeDisposable = new CompositeDisposable();
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void displatFoodDetail(FoodDetailEvent event) {
        Log.d(TAG, "displayFoodDetail: Name: " + event.getFoods().getName());
        Log.d("displatFoodDetail", "Called");
        if (event.isSuccess()) {
            toolbar.setTitle(event.getFoods().getName());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            selectFood = event.getFoods();
            originalPrice = event.getFoods().getPrice();
            txt_money.setText(String.valueOf(originalPrice));
            txt_description.setText(event.getFoods().getDescription());
            Picasso.get().load(event.getFoods().getImage()).into(fab_add_to_cart);

            // If food And size
            if (event.getFoods().isSize() && event.getFoods().isAddon()) {
                // Load Size && Addon From Server
                mDialog.show();
                compositeDisposable.add(APIManage.getApi().getSizeOfFoods(Common.API_KEY, event.getFoods().getMenuId())
                        .observeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(sizeModel -> {
                            // Send Local Data
                            EventBus.getDefault().post(new SizeLoadEvent(true, sizeModel.getResult()));
                            int size = sizeModel.getResult().size();
                            Log.e("SizeLoadEvent", "size is : " + size);
                            // Load addon after load size
                            mDialog.show();
                            compositeDisposable.add(APIManage.getApi().getAddonOfFoods(Common.API_KEY, event.getFoods().getMenuId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(addonModel -> {
                                        mDialog.dismiss();
                                        EventBus.getDefault().post(new AddonLoadEvent(true, addonModel.getResult()));
                                        int addonSize = addonModel.getResult().size();
                                        Log.e("AddonLoadEvent", "addonSize is : " + addonSize);
                                    }, throwable -> {
                                        Toast.makeText(this, "[LOAD ADDON]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));

                        }, throwable -> {
                            mDialog.dismiss();
                            Toast.makeText(this, "[LOAD SIZE]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }));

            } else {
                // If food only have size
                if (event.getFoods().isSize()) {
                    mDialog.show();
                    compositeDisposable.add(APIManage.getApi().getSizeOfFoods(Common.API_KEY, event.getFoods().getMenuId())
                            .observeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(sizeModel -> {
                                // Send Local Data
                                EventBus.getDefault().post(new SizeLoadEvent(true, sizeModel.getResult()));
                                int size = sizeModel.getResult().size();
                                Log.e("SizeLoadEvent", "size is : " + size);
                            }, throwable -> {
                                mDialog.dismiss();
                                Toast.makeText(this, "[LOAD SIZE]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
                }
                // If food only have  addon
                if (event.getFoods().isAddon()) {
                    mDialog.show();
                    compositeDisposable.add(APIManage.getApi().getAddonOfFoods(Common.API_KEY, event.getFoods().getMenuId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(addonModel -> {
                                mDialog.dismiss();
                                EventBus.getDefault().post(new AddonLoadEvent(true, addonModel.getResult()));
                            }, throwable -> {
                                Toast.makeText(this, "[LOAD ADDON]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displaySize(SizeLoadEvent event) {
        Log.d("displaySize", "Called");
        if (event.isSuccess()) {
            for (Size size : event.getSizeList()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked)
                        sizePrice = size.getExtraPrice();

                    calculatePrice();
                    sizeSelected = size.getDescription();
                });

                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                radioButton.setLayoutParams(params);
                radioButton.setText(size.getDescription());
                radioButton.setTag(size.getExtraPrice());
                radio_group_size.addView(radioButton);
            }


        }
    }

    private void calculatePrice() {
        Log.d(TAG, "calculatePrice: called!!");
        extraPrice = 0.0;
        double newPrice;
        extraPrice += sizePrice;
        extraPrice += addOnPrice;

        newPrice = originalPrice + extraPrice;

        txt_money.setText(String.valueOf(newPrice));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayAddon(AddonLoadEvent event) {
        Log.d(TAG, "displayAddon: called!!");
        Log.d(TAG, "displayAddon: "+event.getAddonList().get(0).toString());
        if(event.isSucccess()){
            recycler_addon.setHasFixedSize(true);
            recycler_addon.setLayoutManager(new LinearLayoutManager(this));
            recycler_addon.setAdapter(new AddonAdapter(FoodDetailActivity.this, event.getAddonList()));
        }
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void priceChange(AddOnEventChange eventChange) {
        Log.d(TAG, "priceChange: called!!");
        if (eventChange.isAdd()) {
            addOnPrice += eventChange.getAddon().getExtraPrice();
        } else {
            addOnPrice -= eventChange.getAddon().getExtraPrice();
        }

        calculatePrice();
    }


}

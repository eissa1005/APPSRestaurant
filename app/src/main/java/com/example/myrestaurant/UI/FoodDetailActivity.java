package com.example.myrestaurant.UI;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.Json;
import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Adapter.AddonAdapter;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.DAO.CartDatabase;
import com.example.myrestaurant.Model.DAO.CartItem;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    @BindView(R.id.radio_Small)
    RadioButton radio_Small;
    @BindView(R.id.radio_Medium)
    RadioButton radio_Medium;
    @BindView(R.id.radio_Large)
    RadioButton radio_Large;
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
    boolean chekedStatus = false;


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
        Log.d(TAG, "onCreate :Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        init();
        initView();
    }

    private void initView() {
        Log.d(TAG, "initView : Called");
        ButterKnife.bind(activity);
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_item_from_left);
        // ADD Cart
        fab_add_to_cart.setOnClickListener((v) -> {
            CartItem cartItem = new CartItem();
            cartItem.setFoodId(selectFood.getFoodID());
            cartItem.setFoodName(selectFood.getName());
            cartItem.setFoodPrice(selectFood.getPrice());
            cartItem.setFoodImage(selectFood.getImage());
            cartItem.setFoodQuantity(1);
            cartItem.setUserPhone(Common.currentUser.getUserPhone());
            cartItem.setRestaurantId(Common.currentRestaurant.getRestaurantId());
           // cartItem.setFoodAddon(new Gson().toJson(Common.addonList));
           // cartItem.setFoodSize(sizeSelected);
            cartItem.setFoodExtraPrice(extraPrice);
            cartItem.setFbid(Common.currentUser.getfBID());
            compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(activity, "Added To Cart", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(activity, "[ADD CART]"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        });
        btn_view_cart.setOnClickListener(v -> {
            startActivity(new Intent(FoodDetailActivity.this, CartListActivity.class));
        });
    }
        private void init () {
            mDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).build();
            compositeDisposable = new CompositeDisposable();
            cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
        }

        @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
        public void displayFoodDetail (FoodDetailEvent event){
            Log.d(TAG, "displayFoodDetail: called!!");
            Log.d(TAG, "displayFoodDetail: Name: " + event.getFoods().getName());
            if (event.isSuccess()) {
                toolbar.setTitle(event.getFoods().getName());

                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                selectFood = event.getFoods();
                originalPrice = event.getFoods().getPrice();

                txt_money.setText(String.valueOf(originalPrice));
                txt_description.setText(event.getFoods().getDescription());
                Picasso.get().load(event.getFoods().getImage()).into(img_food_detail);

                if (event.getFoods().isSize() && event.getFoods().isAddon()) {
                    // Load size and addon from server
                    mDialog.show();
                    compositeDisposable.add(APIManage.getApi().getSizeOfFoods(Common.API_KEY, event.getFoods().getFoodID())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(sizeModel -> {
                                // Send local event bust
                                EventBus.getDefault().post(new SizeLoadEvent(true, sizeModel.getResult()));

                                // Load addon after load size
                                mDialog.show();
                                compositeDisposable.add(APIManage.getApi().getAddonOfFoods(Common.API_KEY, event.getFoods().getFoodID())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(addonModel -> {
                                            mDialog.dismiss();
                                            EventBus.getDefault().post(new AddonLoadEvent(true, addonModel.getResult()));
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
                        compositeDisposable.add(APIManage.getApi().getSizeOfFoods(Common.API_KEY, event.getFoods().getFoodID())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(sizeModel -> {
                                    // Send local event bust
                                    EventBus.getDefault().post(new SizeLoadEvent(true, sizeModel.getResult()));

                                }, throwable -> {
                                    mDialog.dismiss();
                                    Toast.makeText(this, "[LOAD SIZE]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
                    }

                    // If food only have  addon
                    if (event.getFoods().isAddon()) {
                        compositeDisposable.add(APIManage.getApi().getAddonOfFoods(Common.API_KEY, event.getFoods().getFoodID())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(addonModel -> {
                                    mDialog.dismiss();
                                    Log.d(TAG, "displayFoodDetail: " + addonModel.getResult().size());
                                    EventBus.getDefault().post(new AddonLoadEvent(true, addonModel.getResult()));
                                }, throwable -> {
                                    Toast.makeText(this, "[LOAD ADDON]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
                    }
                }
            }
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void displaySize (SizeLoadEvent eventSize){
            Log.d(TAG, "displaySize: called!!");
            if (eventSize.isSuccess()) {
                // Create radio button base on size length
                for (Size size : eventSize.getSizeList()) {
                    if (size.getId() == 1) {
                        radio_Small.setText(size.getDescription());
                        radio_Small.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (isChecked) {
                                chekedStatus = true;
                                sizePrice += size.getExtraPrice();
                                calculatePrice();
                                sizeSelected = size.getDescription();
                            } else {
                                chekedStatus = false;
                                sizePrice -= size.getExtraPrice();
                                calculatePrice();
                            }
                        });
                    }
                    if (size.getId() == 2) {
                        radio_Medium.setText(size.getDescription());
                        radio_Medium.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (isChecked) {
                                chekedStatus = true;
                                sizePrice += size.getExtraPrice();
                                calculatePrice();
                                sizeSelected = size.getDescription();
                            } else {
                                chekedStatus = false;
                                sizePrice -= size.getExtraPrice();
                                calculatePrice();
                            }
                        });
                    }
                    if (size.getId() == 3) {
                        radio_Large.setText(size.getDescription());
                        radio_Large.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (isChecked) {
                                chekedStatus = true;
                                sizePrice += size.getExtraPrice();
                                calculatePrice();
                                sizeSelected = size.getDescription();
                            } else {
                                chekedStatus = false;
                                sizePrice -= size.getExtraPrice();
                                calculatePrice();
                            }
                        });
                    }
                    Log.d("mPrice", String.valueOf(size.getExtraPrice()));
                    Log.d("mTexxt", size.getDescription());
                    Log.d("chekedSmall", String.valueOf(chekedStatus));
                    //radio_group_size.addView(radioButton);
                }
            }

        }

        private void calculatePrice () {
            Log.d(TAG, "calculatePrice: called!!");
            extraPrice = 0.0;
            double newPrice;
            extraPrice += sizePrice;
            extraPrice += addOnPrice;

            newPrice = originalPrice + extraPrice;

            txt_money.setText(String.valueOf(newPrice));
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void displayAddon (AddonLoadEvent event){
            Log.d(TAG, "displayAddon: called!!");
            Log.d(TAG, "displayAddon: " + event.getAddonList().get(0).toString());
            if (event.isSucccess()) {
                recycler_addon.setHasFixedSize(true);
                recycler_addon.setLayoutManager(new LinearLayoutManager(this));
                recycler_addon.setAdapter(new AddonAdapter(FoodDetailActivity.this, event.getAddonList()));
            }
        }

        @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
        public void priceChange (AddOnEventChange eventChange){
            Log.d(TAG, "priceChange: called!!");
            if (eventChange.isAdd()) {
                addOnPrice += eventChange.getAddon().getExtraPrice();
            } else {
                addOnPrice -= eventChange.getAddon().getExtraPrice();
            }

            calculatePrice();
        }


    }

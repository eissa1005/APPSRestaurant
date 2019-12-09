package com.example.myrestaurant.UI;

import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.EventBus.SendTotalCashEvent;
import com.example.myrestaurant.R;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlaceOrderActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = PlaceOrderActivity.class.getSimpleName();

    private static final int REQUEST_BRAINTREE_CODE = 7777;

    @BindView(R.id.edt_date)
    EditText edt_date;
    @BindView(R.id.txt_total_cash)
    TextView txt_total_cash;
    @BindView(R.id.txt_user_phone)
    TextView txt_user_phone;
    @BindView(R.id.txt_user_address)
    TextView txt_user_address;
    @BindView(R.id.txt_new_address)
    TextView txt_new_address;
    @BindView(R.id.btn_add_new_address)
    Button btn_add_new_address;
    @BindView(R.id.chb_default_address)
    CheckBox chb_default_address;
    @BindView(R.id.rdi_cod)
    RadioButton rdi_cod;
    @BindView(R.id.rdi_online_payment)
    RadioButton rdi_online_payment;
    @BindView(R.id.btn_proceed)
    Button btn_proceed;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean isSelectedDate = false;
    private boolean isAddNewAddress = false;

    private CartDataSource mCartDataSource;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Log.d(TAG, "onCreate: started!!");

        init();
        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.place_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_user_phone.setText(Common.currentUser.getUserPhone());
        txt_new_address.setText(Common.currentUser.getAddress());

        //new address
        btn_add_new_address.setOnClickListener((v -> {
            isAddNewAddress = true;
            chb_default_address.setChecked(false);
        }));
        Context context;
        @SuppressLint("InflateParams") View layout_add_new_address = LayoutInflater.from(PlaceOrderActivity.this)
                .inflate(R.layout.layout_add_new_address, null);
        EditText edt_new_address = layout_add_new_address.findViewById(R.id.txt_new_address);
        edt_new_address.setText(txt_new_address.getText().length());

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(PlaceOrderActivity.this)
                .setTitle("Add New Address")
                .setView(layout_add_new_address)
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("ADD", (dialog, which) -> {
                    txt_new_address.setText(edt_new_address.getText().toString());
                });

        androidx.appcompat.app.AlertDialog addNewAdressDialog = builder.create();
        addNewAdressDialog.show();

        edt_date.setOnClickListener((v -> {

            Calendar now = Calendar.getInstance();
            DatePickerDialog pickerDialog = DatePickerDialog.newInstance(PlaceOrderActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_WEEK));
            pickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
        }));
        btn_proceed.setOnClickListener(v -> {
            if (!isSelectedDate) {
                Toast.makeText(this, "Please select Date", Toast.LENGTH_SHORT).show();
                return;
            } else {
                isSelectedDate = true;
                String dateString = edt_date.getText().toString();
                @SuppressLint("SimpleDateFormat")
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date orderDate = dateFormat.parse(dateString);
                    Calendar calendar = Calendar.getInstance();
                    Date currentDate = dateFormat.parse(dateFormat.format(calendar.getTime()));
                    if (!DateUtils.isToday(currentDate.getTime())) {
                        if (orderDate.before(currentDate)) {
                            Toast.makeText(this, "Please choose current date or future day", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!isAddNewAddress) {
                    if (!chb_default_address.isChecked()) {
                        Toast.makeText(this, "Please choose default Adress or set new address", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (rdi_cod.isChecked()) {
                    getOrderNumber(false);
                } else if (rdi_online_payment.isChecked()) {
                    getOrderNumber(true);
                }
            }
        });
    }

    private void getOrderNumber(boolean isOnlinePayment) {
        Log.d(TAG, "getOrderNumber: called!!");
        mDialog.show();
        mDialog.show();
        if (!isOnlinePayment) {
            String address = chb_default_address.isChecked() ? txt_user_address.getText().toString() : txt_new_address.getText().toString();
            // Get All CartItems
            compositeDisposable.add(mCartDataSource.getAllCart(Common.currentUser.getFBID(), Common.currentRestaurant.getRestaurantId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cartItems -> {
                        // Create Orders And GET Number from Server
                        compositeDisposable.add(APIManage.getApi().createOrder(Common.API_KEY,
                                Common.currentUser.getFBID(),
                                Common.currentUser.getUserPhone(),
                                Common.currentUser.getName(),
                                address,
                                1,
                                edt_date.getText().toString(),
                                Common.currentRestaurant.getRestaurantId(),
                                "NONE",
                                true,
                                Double.parseDouble(txt_total_cash.getText().toString()),
                                cartItems.size())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(createOrderModel -> {
                                    if (createOrderModel.isSuccess()) {
                                        // After have order number, we will update all item of this order to order Detail
                                        // First, select Cart items
                                        compositeDisposable.add(APIManage.getApi().updateOrder(Common.API_KEY,
                                                String.valueOf(createOrderModel.getResult().get(0).getOrderNumber()),
                                                new Gson().toJson(cartItems))
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(updateOrderModel -> {
                                                    if (updateOrderModel.isSuccess()) {
                                                        // After update item, we will clear cart and show message success
                                                        mCartDataSource.cleanCart(Common.currentUser.getFBID(),
                                                                Common.currentRestaurant.getRestaurantId())
                                                                .subscribeOn(Schedulers.io())
                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                .subscribe(new SingleObserver<Integer>() {
                                                                    @Override
                                                                    public void onSubscribe(Disposable d) {

                                                                    }

                                                                    @Override
                                                                    public void onSuccess(Integer integer) {
                                                                        Toast.makeText(PlaceOrderActivity.this, "Orders :Placed", Toast.LENGTH_SHORT).show();
                                                                        Intent homeActivity = new Intent(PlaceOrderActivity.this, HomeActivity.class);
                                                                        homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        startActivity(homeActivity);
                                                                        finish();
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        Toast.makeText(PlaceOrderActivity.this, "[CLEAR CART]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                    if (mDialog.isShowing())
                                                        mDialog.dismiss();
                                                }, throwable -> {
                                                    mDialog.show();
                                                    Toast.makeText(this, "[UPDATE ORDER]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                }));
                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(this, "[CREATE ORDER]" + createOrderModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }, throwable -> {
                                    mDialog.dismiss();
                                    Toast.makeText(this, "[CREATE ORDER]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
                    }, throwable -> {
                        Toast.makeText(this, "[GET ALL CART]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }));
        }
    }

    private void init() {
        Log.d(TAG, "init: called!!");
        mDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
    }

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
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void setTotalCash(SendTotalCashEvent event) {
        txt_total_cash.setText(String.valueOf(event.getCash()));
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Implement late
        isSelectedDate = true;
        edt_date.setText(new StringBuilder("")
                .append(monthOfYear + 1)
                .append("/")
                .append(dayOfMonth)
                .append("/")
                .append(year));
    }
}



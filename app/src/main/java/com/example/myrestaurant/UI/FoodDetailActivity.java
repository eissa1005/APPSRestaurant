package com.example.myrestaurant.UI;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Model.DAO.CartDataSource;
import com.example.myrestaurant.Model.DAO.CartDatabase;
import com.example.myrestaurant.Model.DAO.LocalCartDataSource;
import com.example.myrestaurant.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;

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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        init();
        initView();
    }

    private void initView() {
        Log.d("initView","Called");
        ButterKnife.bind(activity);
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(activity,R.anim.layout_item_from_left);
    }

    private void init() {
        mDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).build();
        compositeDisposable =new CompositeDisposable();
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDAO());
    }

}

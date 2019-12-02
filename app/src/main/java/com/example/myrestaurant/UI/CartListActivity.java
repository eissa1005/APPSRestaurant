package com.example.myrestaurant.UI;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.braintreepayments.api.dropin.BaseActivity;
import com.example.myrestaurant.R;
import butterknife.BindView;
public class CartListActivity extends BaseActivity {

    @BindView(R.id.recycler_cart)
    RecyclerView  recycler_cart;
    @BindView(R.id.txt_final_price)
    TextView txt_final_price;
    @BindView(R.id.btn_order)
    Button btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
    }
}

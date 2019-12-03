package com.example.myrestaurant.UI;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Adapter.FavoriteAdapter;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("Registered")
public class FavoriteActivity extends BaseActivity {

    private static final String TAG=FavoriteActivity.class.getSimpleName();
    @BindView(R.id.recycler_fav)
    RecyclerView recycler_fav;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    FavoriteAdapter adapter;
    AlertDialog mDialog;
    CompositeDisposable compositeDisposable;
    private LayoutAnimationController mLayoutAnimationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Create Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        init();
        initView();
        getAllFavorite();

    }

    private void getAllFavorite() {
        mDialog.show();
        Log.d(TAG,"getAllFavorite : Called");
        compositeDisposable.add(APIManage.getApi().getFavoriteById(Common.API_KEY,Common.currentUser.getfBID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteModel -> {
                    if (favoriteModel.isSuccess()) {
                        mDialog.dismiss();
                        adapter = new FavoriteAdapter(FavoriteActivity.this, favoriteModel.getResult());
                        recycler_fav.setAdapter(adapter);
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(this, "[Load Favorite]" + favoriteModel.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }, throwable -> {
                    mDialog.dismiss();
                    Toast.makeText(this, "[Load Favorite]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void initView() {
        ButterKnife.bind(activity);
        toolbar.setTitle(getString(R.string.menu_fav));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_fav.setLayoutManager(layoutManager);
        recycler_fav.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        mLayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_item_from_left);
    }

    private void init() {
        mDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        if (adapter != null) {
            adapter = null;
        }
        super.onDestroy();
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

package com.example.myrestaurant.UI;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myrestaurant.API.APIManage;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btnSignIn)
    Button loginUser;
    @BindView(R.id.btnRegister)
    Button Register;
    @BindView(R.id.edit_Phone)
    EditText edit_Phone;
    @BindView(R.id.edit_Password)
    EditText edit_Password;
    //private FirebaseAuth mAuth;
    AlertDialog mDialog;
    CompositeDisposable compositeDisposable;
    private static final int APP_REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(activity);
        init();
    }

    private void init() {
        // Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();
        mDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).build();
        compositeDisposable = new CompositeDisposable();
    }

    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false).build();
        }
        mDialog.setMessage("...Loading");
        mDialog.show();
    }

    public void hideProgressDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    @OnClick(R.id.btnSignIn)
    public void loginUser(View view) {
        Log.e("Login", "Login Called");
        String userPhone = edit_Phone.getText().toString();
        String userPassword = edit_Password.getText().toString();
        if ((!TextUtils.isEmpty(userPhone)) && (!TextUtils.isEmpty(userPassword))) {
            showProgressDialog();
            // [START create_user_with_email]
            compositeDisposable.add(APIManage.getApi().GetUserByPhone(Common.API_KEY, userPhone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userResponse -> {
                        if (userResponse.isSuccess()) {
                            Common.currentUser =userResponse.getResult().get(0);
                            if (userPhone.equals(Common.currentUser.getUserPhone()) && userPassword.equals(Common.currentUser.getUserPassword())) {
                                Toast.makeText(activity, " UserLogin : Success ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else {
                                Toast.makeText(activity, "UserPhone Or UserPassword Invalid ,Please Try Again ", Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            }
                        } else {
                            Log.d("UserLogin", userResponse.getMessage());
                            Toast.makeText(activity, "[UserLogin]" + userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                        }
                    }, throwable -> {
                        Toast.makeText(activity, "[User Throwable]" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        hideProgressDialog();
                    })
            );

        }
    }

/*
    @OnClick(R.id.btnRegister)
    public void Register(View view) {
        Log.e("Register", "Register Called");
        String email = edit_Phone.getText().toString();
        String password = edit_Password.getText().toString();
        if ((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(password))) {
            showProgressDialog();
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("isSuccessful", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("CreateUserWithEmail", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                            // [START_EXCLUDE]
                            hideProgressDialog();
                            // [END_EXCLUDE]
                        }
                    });

        }
    }
 */

}

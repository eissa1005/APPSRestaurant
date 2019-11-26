package com.example.myrestaurant.UI;

import androidx.annotation.NonNull;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final int APP_REQUEST_CODE = 99;
    @BindView(R.id.btnSignIn)
    Button loginUser;
    @BindView(R.id.btnRegister)
    Button Register;
    @BindView(R.id.edit_Name)
    EditText edit_Name;
    @BindView(R.id.edit_Password)
    EditText edit_Password;
    private FirebaseAuth mAuth;

    AlertDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(activity);
        init();
        loginUser.setOnClickListener(this);
        Register.setOnClickListener(this);
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).build();
    }
    public void showProgressDialog(){
        if(mDialog== null){
            mDialog = new SpotsDialog.Builder()
                    .setContext(this)
                   .setCancelable(false).build();
        }
        mDialog.setMessage("...Loading");
        mDialog.show();
    }

    public  void hideProgressDialog(){
        if(mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    public void loginUser() {

    }


    public void Register() {
        Log.e("Register","Register Called");
        String email = edit_Name.getText().toString();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                loginUser();
                break;
            case R.id.btnRegister:
                Register();
                break;
        }
    }
}

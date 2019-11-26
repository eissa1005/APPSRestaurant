package com.example.myrestaurant.UI;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.myrestaurant.Base.BaseActivity;
import com.example.myrestaurant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends BaseActivity {

    private static final int APP_REQUEST_CODE = 99;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.btnRegister)
    Button btnRegister;
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
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDialog = new SpotsDialog.Builder().setContext(activity).setCancelable(false).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @OnClick(R.id.btnSignIn)
    public void loginUser(@Nullable View view) {

    }

    @OnClick(R.id.btnRegister)
    public void Register(View view) {
        String name = edit_Name.getText().toString();
        String pass = edit_Password.getText().toString();
        if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(pass))) {
            FirebaseUser mUser = mAuth.getCurrentUser();
                       mAuth.createUserWithEmailAndPassword(name, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          mUser.getIdToken(true)
                          .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                              @Override
                              public void onComplete(@NonNull Task<GetTokenResult> task) {
                                  String IdToken = task.getResult().getToken();
                                  Log.e("IdToken",IdToken);
                              }
                          });
                        }
                    });
        }
    }


}

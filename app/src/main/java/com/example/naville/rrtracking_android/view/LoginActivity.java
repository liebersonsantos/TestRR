package com.example.naville.rrtracking_android.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naville.rrtracking_android.MainActivity;
import com.example.naville.rrtracking_android.R;
import com.example.naville.rrtracking_android.model.Login;
import com.example.naville.rrtracking_android.model.User;
import com.example.naville.rrtracking_android.network.RestClient;
import com.example.naville.rrtracking_android.presenter.PresenterLogin;
import com.example.naville.rrtracking_android.util.CustomAlertDialog;
import com.example.naville.rrtracking_android.util.Mask;
import com.example.naville.rrtracking_android.util.MyLocation;
import com.example.naville.rrtracking_android.util.Preferences;
import com.example.naville.rrtracking_android.util.SHA1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {

    private TextView btnForgotPassword;
    private TextView btnPrivacyPolicy;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private Button buttonLogin;

    private User user;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    private ConstraintLayout constraintLayout;
    private static final int REQUEST_WRITE_PERMISSIONS = 2;

    PresenterLogin presenterLogin = new PresenterLogin(this);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        getPermissions();

        MyLocation.fusedLocation(this);
        MyLocation.getMyLocation(this);

        progressDialog = new ProgressDialog(this);
        user = new User();
        preferences = new Preferences(this);

        loginAccess();
        forgotPassWord();
        privacyPolicy();
        hideKeyBoard();

    }

    private void privacyPolicy() {
        btnPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog.customDialogPrivacyPolicyAnimated(LoginActivity.this);
            }
        });
    }

    private void forgotPassWord() {
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomAlertDialog.customDialogAnimated(LoginActivity.this, R.layout.activity_forgot_password);
            }
        });
    }

    private void loginAccess() {
        buttonLogin.setOnClickListener(v -> {

            dialogLogin();

        String email = textInputEditTextEmail.getText().toString().trim();
        String pass = textInputEditTextPassword.getText().toString().trim();


        /*Setando os valores do objeto User*/
        try {
            user.setPassWord(SHA1.codingSHA1(pass));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user.setEmail(email);

        /*Limpando os dados do sharedPreferences*/
        preferences.clearPreferences();
        /*Setando os valores do sharedPrefereces*/
        preferences.saveData(user.getEmail(), user.getPassWord(), pass);

        presenterLogin.validateLogin(user, email, pass);

        callingApi();
        });
    }

    private void hideKeyBoard() {
        constraintLayout = findViewById(R.id.constraint_id);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeybord();
            }
        });
    }

    private void initViews() {
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);

        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);

        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnPrivacyPolicy = findViewById(R.id.btnPrivacyPolicy_id);
        buttonLogin = findViewById(R.id.btnLogin);
    }

    private void hideSoftKeybord() {

        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void callingApi() {
        RestClient.getInstanceLogin(this).login().enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    user.setUserId(response.body().getUserId());
                    user.setUserName(response.body().getUserName());

                    succesfulLogin();

                } else {
                    Toast.makeText(LoginActivity.this, "Verifique dados inseridos", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Log.i("TAG", "onResponseERRO: ERRO");
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void dialogLogin() {
        progressDialog.setMessage("Analisando Dados");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void emptyFields() {

        textInputLayoutEmail.setErrorEnabled(true);
        textInputLayoutEmail.setError("Preencha o campo e-mail!");
        textInputLayoutPassword.setErrorEnabled(true);
        textInputLayoutPassword.setError("Preencha o campo senha!");

    }

    public void errorEmailEmpty() {

        textInputLayoutEmail.setErrorEnabled(true);
        textInputLayoutEmail.setError("Preencha o campo e-mail!");

    }

    public void errorPasswordEmpty() {

        textInputLayoutPassword.setErrorEnabled(true);
        textInputLayoutPassword.setError("Preencha o campo senha!");
    }

    public void succesfulLogin() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);

    }

    public void emailInvalid() {
        Toast.makeText(this, "Email inválido!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        textInputEditTextEmail.setText(preferences.getEmail());
        textInputEditTextPassword.setText((preferences.getSenhaNormal()));
    }

    private boolean getPermissions() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            return true;
        }
        if (checkSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(INTERNET)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Atenção");
            builder.setMessage("Permissão necessária para acessar o aplicativo");
            builder.setNegativeButton("Não", (DialogInterface dialog, int which) -> {
                dialog.dismiss();
            });
            builder.setPositiveButton("Sim", (DialogInterface dialog, int which) -> {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_WRITE_PERMISSIONS);
            });
            builder.create();
            builder.show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_WRITE_PERMISSIONS);

        }
        return false;
    }
}

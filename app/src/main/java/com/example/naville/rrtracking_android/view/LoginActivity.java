package com.example.naville.rrtracking_android.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnForgotPassword)
    TextView btnForgotPassword;
    @BindView(R.id.btnPrivacyPolicy)
    TextView btnPrivacyPolicy;
    @BindView(R.id.textInputLayoutEmail)
    TextInputLayout textInputLayoutEmail;
    @BindView(R.id.textInputLayoutPassword)
    TextInputLayout textInputLayoutPassword;
    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText textInputEditTextEmail;
    @BindView(R.id.textInputEditTextPassword)
    TextInputEditText textInputEditTextPassword;

    private User user;
    private Preferences preferences;
    private ProgressDialog progressDialog;

    PresenterLogin presenterLogin = new PresenterLogin(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        MyLocation.fusedLocation(this);
        MyLocation.getMyLocation(this);

        progressDialog = new ProgressDialog(this);
        user = new User();
        preferences = new Preferences(this);

    }

    @OnClick(R.id.btnLogin)
    void loginAccess(View view) {

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

        RestClient.getInstanceLogin(this).login().enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    Log.i("TAG", "onResponse: " + response.body());
                    Log.i("TAG", "onResponse:getResult " + response.body().getResult());
                    Log.i("TAG", "onResponse:getUserName " + response.body().getUserName());
                    Log.i("TAG", "onResponse:getStatus " + response.body().getStatus());
                    Log.i("TAG", "onResponse:getUserId " + response.body().getUserId());

                    user.setUserId(response.body().getUserId());
                    user.setUserName(response.body().getUserName());

                    succesfulLogin();

                } else {

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
        progressDialog.setMessage("Analisando Dados inseridos");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @OnClick(R.id.btnForgotPassword)
    public void forgotPassword() {

//        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        CustomAlertDialog.customDialogAnimated(this, R.layout.activity_forgot_password);

    }

    @OnClick(R.id.btnPrivacyPolicy)
    public void privacyPolicty() {
        startActivity(new Intent(LoginActivity.this, PrivacyPolicyActivity.class));
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
//        Toast.makeText(this, "Login com êxito!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

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
}

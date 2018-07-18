package com.example.naville.rrtracking_android.presenter;

import android.content.Intent;
import android.util.Log;

import com.example.naville.rrtracking_android.model.Login;
import com.example.naville.rrtracking_android.model.User;
import com.example.naville.rrtracking_android.network.RestClient;
import com.example.naville.rrtracking_android.view.LoginActivity;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLogin {

    private static final String TAG = "RESPOSTA_DO_SERVIDOR";
    private LoginActivity loginActivity;

    public PresenterLogin(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void validateLogin(User user, String email, String passWord){
        if(email.isEmpty()){
            loginActivity.errorEmailEmpty();
        }
        if(passWord.isEmpty()){
            loginActivity.errorPasswordEmpty();
        }
        if(passWord.isEmpty() && email.isEmpty()){
            loginActivity.emptyFields();
        }
        if(!email.isEmpty() && !passWord.isEmpty()){

//            loginActivity.succesfulLogin();


        }
    }




}

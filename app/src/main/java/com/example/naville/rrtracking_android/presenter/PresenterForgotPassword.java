package com.example.naville.rrtracking_android.presenter;

import android.widget.Toast;

import com.example.naville.rrtracking_android.util.Mask;
import com.example.naville.rrtracking_android.view.LoginActivity;

public class PresenterForgotPassword {

//    private ForgotPasswordActivity presenterForgotPassword;

    private LoginActivity loginActivity;

    public PresenterForgotPassword(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void validateEmail(String email) {
        if (email != null) {

            Toast.makeText(loginActivity, "OKOKOK", Toast.LENGTH_SHORT).show();
        }else {

            loginActivity.errorEmailEmpty();
        }

        if(Mask.Util.validateEmail(email)){
            Toast.makeText(loginActivity, "BOA!", Toast.LENGTH_SHORT).show();
            }else{
                loginActivity.emailInvalid();
            }
        }
    }




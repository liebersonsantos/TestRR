package com.example.naville.rrtracking_android.util;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naville.rrtracking_android.R;
import com.example.naville.rrtracking_android.model.RecoverPasswordResponse;
import com.example.naville.rrtracking_android.model.User;
import com.example.naville.rrtracking_android.network.RestClient;
import com.example.naville.rrtracking_android.presenter.PresenterForgotPassword;
import com.example.naville.rrtracking_android.view.LoginActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAlertDialog {

    private static final String TAG = "RECOVERY_RESULT";
    private static AlertDialog alertDialog = null;
    private static LoginActivity loginActivity = new LoginActivity();
    private static PresenterForgotPassword presenterForgotPassword = new PresenterForgotPassword(loginActivity);


    public static void customDialogAnimated(Activity activity, int customLayout) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(customLayout, null);

        Button btnProceedRecoveryPassword;
        ImageView btnCloseRecoveryPassword;
        final TextInputEditText textInputEditTextEmail;

        btnCloseRecoveryPassword = dialogView.findViewById(R.id.btnCloseRecoveryPassword);
        btnProceedRecoveryPassword = dialogView.findViewById(R.id.btnProceedRecoveryPassword);
        textInputEditTextEmail = dialogView.findViewById(R.id.textInputEditTextEmailForgot);



        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();



        btnCloseRecoveryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnProceedRecoveryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textInputEditTextEmail.getText().toString().trim();

                if (email.isEmpty()){
                    Toast.makeText(activity, "Preencha o campo email", Toast.LENGTH_SHORT).show();

                }else {
                    RestClient.getInstanceRecoveryPassWord().recoverPassWord(email).enqueue(new Callback<RecoverPasswordResponse>() {
                        @Override
                        public void onResponse(Call<RecoverPasswordResponse> call, Response<RecoverPasswordResponse> response) {

                            if (response.isSuccessful()){
                                Log.i(TAG, "onResponse:getResult " + response.body().getResult());
                                Toast.makeText(activity, "E-mail enviado com sucesso! Sua nova senha será enviada em breve.", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }else {
                                Toast.makeText(activity, "ERRO", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "onResponse: " + response.body());

                            }

                        }

                        @Override
                        public void onFailure(Call<RecoverPasswordResponse> call, Throwable t) {

                            Log.i(TAG, "onFailure: " + t.getMessage());

                        }
                    });

                }
//                presenterForgotPassword.validateEmail(textInputEditTextEmail.getText().toString().trim());
            }
        });

        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();


    }

    public static void customDialogProfileAnimated(Activity activity, User user) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_see_profile, null);

        Button btnCloseProfile;
        TextView textNameProfile;
        TextView textIdProfile;

        btnCloseProfile = dialogView.findViewById(R.id.btn_close_profile);
        textNameProfile = dialogView.findViewById(R.id.text_name_profile);
        textIdProfile = dialogView.findViewById(R.id.text_id_profile);

        textNameProfile.setText(user.getUserName());
        textIdProfile.setText(String.valueOf(user.getUserId()));

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        btnCloseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.dialog_animation_profile;
        alertDialog.show();
    }

    public static void customDialogPrivacyPolicyAnimated(Activity activity) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_privacy_policy, null);


        Button btnClosePrivacy = dialogView.findViewById(R.id.btn_close_privacy);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        btnClosePrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.dialog_animation_privacy_policy;
        alertDialog.show();
    }


    public static void customDialogFullscreen() {

        // builder = new android.support.v7.app.AlertDialog.Builder(activity);
        // dialog = builder.create();
        // view = LayoutInflater.from(activity).inflate(R.layout.activity_forgot_password, null);
        // builder.setView(view).setCancelable(false);
        // dialog.show();

    }

    public static void customAlertDialogIntent(final Activity activity, final Class classe, String mensagem){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Aviso");
        builder.setIcon(activity.getResources().getDrawable(R.drawable.ic_warning_black_24dp));
        builder.setMessage(mensagem);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.startActivity(new Intent(activity, classe));

            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void custoDialogMsg(Activity activity, String titulo, String mensagem) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());

        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }

    public static void customDialogImageZoom(Activity activity, String imageUrl) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_image_zoom, null);

        Button btnCloseImageZoom;
        PhotoView photoView;
        ProgressBar progressBarZoom;

        btnCloseImageZoom = dialogView.findViewById(R.id.btn_close_image_zoom);
        photoView = dialogView.findViewById(R.id.image_zoom);
        progressBarZoom = dialogView.findViewById(R.id.progress_zoom);

        Log.i(TAG, "customDialogImageZoom: "+ Constants.URL_IMAGE + imageUrl);

//        photoView.setImageURI(Uri.parse(Constants.URL_IMAGE + imageUrl));

        ImageUtil.loadImage(Constants.URL_IMAGE + imageUrl, photoView, progressBarZoom, R.drawable.logo);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        btnCloseImageZoom.setOnClickListener(v -> alertDialog.dismiss());

        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.dialog_animation_profile;
        alertDialog.show();
    }


}

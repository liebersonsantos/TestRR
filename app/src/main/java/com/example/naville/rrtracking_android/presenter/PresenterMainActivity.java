package com.example.naville.rrtracking_android.presenter;

import com.example.naville.rrtracking_android.MainActivity;
import com.example.naville.rrtracking_android.util.CustomAlertDialog;
import com.example.naville.rrtracking_android.view.LoginActivity;

public class PresenterMainActivity {

    private MainActivity mainActivity;

    public PresenterMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void lougoutMainActivity(){

        CustomAlertDialog.customAlertDialogIntent(mainActivity, LoginActivity.class, "Deseja realmente sair?");

    }

    public void aboutScreen(){
        CustomAlertDialog.custoDialogMsg(mainActivity, "Sobre", "App versão 1.0 \nDesenvolvido por Naville Brasil 2018");
    }


}

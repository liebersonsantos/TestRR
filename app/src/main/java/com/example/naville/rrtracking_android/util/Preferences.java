package com.example.naville.rrtracking_android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {


    private Context context;
    private SharedPreferences preferences;
    private final String FILE_NAME = "preferences.login";
    private SharedPreferences.Editor editor;

    private final String EMAIL_KEY = "emailLogado";
    private final String PASSWORD_KEY = "senhaLogada";
    private final String PASSWORD_NORMAL= "senhaNormal";

    public Preferences( Context contextParametro){

        context = contextParametro;
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    public void saveData( String emailLog, String passWordLog, String passwordNormal){

        editor.putString(EMAIL_KEY, emailLog);
        editor.putString(PASSWORD_KEY, passWordLog);
        editor.putString(PASSWORD_NORMAL, passwordNormal);
        editor.commit();

    }

    public String getEmail(){
        return preferences.getString(EMAIL_KEY, null);
    }

    public String getSenha(){
        return preferences.getString(PASSWORD_KEY, null);
    }

    public String getSenhaNormal() {return preferences.getString(PASSWORD_NORMAL, null);}

    public void clearPreferences(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.clear();
        editor1.commit();
    }

}

package com.example.naville.rrtracking_android.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Mask {


    public abstract static class Util {
        public static String unmask(String s) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[)]", "");
        }

        public static TextWatcher insert(final String mask, final EditText ediTxt) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = Util.unmask(s.toString());
                    String mascara = "";
                    if (isUpdating) {
                        old = str;
                        isUpdating = false;
                        return;
                    }
                    int i = 0;
                    for (char m : mask.toCharArray()) {
                        if (m != '#' && str.length() > old.length()) {
                            mascara += m;
                            continue;
                        }
                        try {
                            mascara += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    ediTxt.setText(mascara);
                    ediTxt.setSelection(mascara.length());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

            };
        }


        public static boolean validateCPF(String CPF) {
            CPF = Util.unmask(CPF);
            if (CPF.equals("00000000000") || CPF.equals("11111111111")
                    || CPF.equals("22222222222") || CPF.equals("33333333333")
                    || CPF.equals("44444444444") || CPF.equals("55555555555")
                    || CPF.equals("66666666666") || CPF.equals("77777777777")
                    || CPF.equals("88888888888") || CPF.equals("99999999999")) {
                return false;
            }
            char dig10, dig11;
            int sm, i, r, num, peso;
            try {
                sm = 0;
                peso = 10;
                for (i = 0; i < 9; i++) {
                    num = (int) (CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }
                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig10 = '0';
                else
                    dig10 = (char) (r + 48);
                sm = 0;
                peso = 11;
                for (i = 0; i < 10; i++) {
                    num = (int) (CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }
                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig11 = '0';
                else
                    dig11 = (char) (r + 48);
                if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                    return (true);
                else
                    return (false);
            } catch (Exception erro) {
                return (false);
            }
        }

        public static boolean validateNotNull(String valor) {

            boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
            return resultado;
        }


        public final static boolean validateEmail(String txtEmail) {
            if (TextUtils.isEmpty(txtEmail) || txtEmail.trim().isEmpty()) {
                return false;
            } else {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches();
            }
        }

        public static void fechaTeclado(LinearLayout lnlLayout, final Activity activity) {

            lnlLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    return false;
                }
            });
        }

        public static boolean isCameraSupported(Context context) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
                Toast.makeText(context, "This device does not have a camera.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

        public static boolean checkPermission(final Context context) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }

    }
}

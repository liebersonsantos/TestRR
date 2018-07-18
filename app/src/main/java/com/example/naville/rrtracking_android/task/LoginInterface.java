package com.example.naville.rrtracking_android.task;

public interface LoginInterface {

    interface Model {

        void login();

    }

    interface View {

        void errorEmptyUser();

        void errorEmptyPassword();

        void successfulLogin();

        void failedLogin();


    }

    interface Presenter {

        void validateLogin();

    }

}

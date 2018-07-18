//package com.example.naville.rrtracking_android.view;
//
//import android.animation.AnimatorInflater;
//import android.animation.AnimatorSet;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//
//import com.example.naville.rrtracking_android.R;
//
//import butterknife.BindView;
//
//public class ForgotPasswordActivity extends AppCompatActivity {
//
//
//    public static AnimatorSet mSetRightOut;
//    public static AnimatorSet mSetLeftIn;
//    public static boolean mIsBackVisible = false;
//
////    @BindView(R.id.card_back)
////    View mCardBackLayout;
////
////    @BindView(R.id.card_front)
////    View mCardFrontLayout;
//
//    public static View mCardBackLayout;
//    public static View mCardFrontLayout;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forgot_password);
//
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        mCardBackLayout = findViewById(R.id.card_back);
//        mCardFrontLayout = findViewById(R.id.card_front);
//
//        loadAnimations();
//        changeCameraDistance();
//
//
//    }
//
//    public void changeCameraDistance() {
//        int distance = 8000;
//        float scale = getResources().getDisplayMetrics().density * distance;
//        mCardFrontLayout.setCameraDistance(scale);
//        mCardBackLayout.setCameraDistance(scale);
//    }
//
//    public  void loadAnimations() {
//        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
//        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
//
//    }
//
//    public void flipCard(View view) {
//        if (!mIsBackVisible) {
//            mSetRightOut.setTarget(mCardFrontLayout);
//            mSetLeftIn.setTarget(mCardBackLayout);
//            mSetRightOut.start();
//            mSetLeftIn.start();
//            mIsBackVisible = true;
//        } else {
//            mSetRightOut.setTarget(mCardBackLayout);
//            mSetLeftIn.setTarget(mCardFrontLayout);
//            mSetRightOut.start();
//            mSetLeftIn.start();
//            mIsBackVisible = false;
//        }
//    }
//
//
//}

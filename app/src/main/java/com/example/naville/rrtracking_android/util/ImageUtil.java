package com.example.naville.rrtracking_android.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ImageUtil {

    public static void loadImage(String url, ImageView imageView, ProgressBar progressBar, int defaultImage) {

        setVisibility(progressBar, View.VISIBLE);

        if (url == null) {
            imageView.setImageResource(defaultImage);
            return;
        }

        Picasso.get()
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .transform(new BitmapTransform(1024, 768))
                .resize(512, 512)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        setVisibility(progressBar, View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        setVisibility(progressBar, View.VISIBLE);

                        Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).error(defaultImage)
                                .transform(new BitmapTransform(1024, 768))
                                .resize(512, 512)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        setVisibility(progressBar, View.GONE);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        setVisibility(progressBar, View.VISIBLE);
                                    }
                                });

                    }
                });
    }

    static class BitmapTransform implements Transformation {

        private int maxWidth;
        private int maxHeight;

        BitmapTransform(int maxWidth, int maxHeight) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth;
            int targetHeight;
            double aspectRatio;

            if (source.getWidth() > source.getHeight()) {
                targetWidth = maxWidth;
                aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                targetHeight = (int) (targetWidth * aspectRatio);
            } else {
                targetHeight = maxHeight;
                aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                targetWidth = (int) (targetHeight * aspectRatio);
            }

            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return maxWidth + "x" + maxHeight;
        }
    }

    private static void setVisibility(View view, int visibility){
        if (view != null) {
            view.setVisibility(visibility);
        }
    }
}

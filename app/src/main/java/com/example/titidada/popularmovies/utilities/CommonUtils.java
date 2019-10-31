package com.example.titidada.popularmovies.utilities;

import android.util.Log;
import android.widget.ImageView;

import com.example.titidada.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CommonUtils {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";


    public static void loadImageByPicasso(ImageView imageView, String imagePath){

        Picasso.get()
                .load(IMAGE_BASE_URL + IMAGE_SIZE + imagePath)
                .error(R.drawable.download)
                .resize(185, 278)
                .centerInside()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("CommonUtils", e.toString());
                    }
                });

    }
}

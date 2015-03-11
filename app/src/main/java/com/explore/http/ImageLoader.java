package com.explore.http;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * A simple wrapper around Volley's ImageLoader.
 *
 * @author vgrec, created on 10/24/14.
 */
public class ImageLoader {

    public static void loadImage(Context context, String url, NetworkImageView imageView) {
        com.android.volley.toolbox.ImageLoader loader = VolleySingleton.getInstance(context).getImageLoader();
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageUrl(url, loader);
    }
}

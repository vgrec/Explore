package com.traveler.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.traveler.R;
import com.traveler.http.ImageLoader;
import com.traveler.utils.ScrimUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ScrimImageHeader extends LinearLayout {

    private Context context;

    @InjectView(R.id.big_image)
    NetworkImageView bigImage;

    @InjectView(R.id.scrim_background)
    ViewGroup scrimBackground;

    @InjectView(R.id.number_of_photos)
    TextView numberOfPhotos;

    public ScrimImageHeader(Context context) {
        super(context);
        init(context);
    }

    public ScrimImageHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrimImageHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_scrim_image_header, this, true);
        ButterKnife.inject(this, this);

        scrimBackground.setBackgroundDrawable(ScrimUtil.makeCubicGradientScrimDrawable(0xaa000000, 8, Gravity.BOTTOM));
    }

    public void setNumberOfPhotos(int number) {
        if (number <= 1) {
            return;
        }

        numberOfPhotos.setText(number + " photos");
        numberOfPhotos.setVisibility(View.VISIBLE);
    }

    public void setImageUrl(String url) {
        ImageLoader.loadImage(context, url, bigImage);
    }
}

package com.traveler.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.traveler.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author vgrec, created on 12/16/14.
 */
public class PreviewImage extends LinearLayout {

    @InjectView(R.id.preview_title)
    TextView titleTextView;

    @InjectView(R.id.preview_image)
    NetworkImageView imageView;

    public PreviewImage(Context context) {
        this(context, null);
    }

    public PreviewImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreviewImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_preview_image, this, true);
        ButterKnife.inject(this, this);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public NetworkImageView getImageView() {
        return imageView;
    }
}

package com.explore.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vgrec.explore.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Displays a loading indicator above everything else while
 * an HTTP request is in progress. If the HTTP request failed,
 * it provides the ability to retry.
 */
public class ProgressView extends LinearLayout {

    private OnClickListener tryAgainClickListener;

    @InjectView(R.id.progress_view_container)
    ViewGroup progressViewContainer;

    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @InjectView(R.id.error_view)
    ViewGroup errorView;

    @InjectView(R.id.error_text)
    TextView errorTextView;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_progress, this, true);
        ButterKnife.inject(this, this);
        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        if (a == null) {
            return;
        }

        int titleResId = a.getResourceId(R.styleable.ProgressView_progressViewErrorText, R.string.cant_load_attractions);
        errorTextView.setText(titleResId);
    }

    @OnClick(R.id.try_again)
    public void onTryAgainClicked(View v) {
        if (tryAgainClickListener != null) {
            progressBar.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
            tryAgainClickListener.onClick(v);
        }
    }

    public void setTryAgainClickListener(OnClickListener tryAgainClickListener) {
        this.tryAgainClickListener = tryAgainClickListener;
    }

    public void show() {
        progressViewContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    public void hide() {
        progressViewContainer.setVisibility(View.GONE);
    }

    public void showError() {
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}

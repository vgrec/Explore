package com.explore.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.explore.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author vgrec, created on 12/8/14.
 */
public class PreviewCard extends LinearLayout {

    private LayoutInflater inflater;
    private OnClickListener viewAllButtonClickListener;

    @InjectView(R.id.card_title)
    TextView cardTitleTextView;

    @InjectView(R.id.content)
    ViewGroup contentContainer;

    public PreviewCard(Context context) {
        this(context, null);
    }

    public PreviewCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreviewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_preview_card, this, true);
        ButterKnife.inject(this, this);
        setAttributes(context, attrs);
    }

    @OnClick(R.id.view_all)
    public void onViewAllButtonClicked(View v) {
        if (viewAllButtonClickListener != null) {
            viewAllButtonClickListener.onClick(v);
        }
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PreviewCard);
        if (a == null) {
            return;
        }

        CharSequence title = a.getText(R.styleable.PreviewCard_previewCardTitle);
        cardTitleTextView.setText(title);

        int iconResourceId = a.getResourceId(R.styleable.PreviewCard_previewCardIcon, R.drawable.ic_launcher);
        cardTitleTextView.setCompoundDrawablesWithIntrinsicBounds(iconResourceId, 0, 0, 0);

        int contentLayoutResId = a.getResourceId(R.styleable.PreviewCard_previewContentLayout, -1);
        if (contentLayoutResId != -1) {
            View contentView = inflater.inflate(contentLayoutResId, this, false);
            contentContainer.addView(contentView);
        }
    }

    public TextView getTitleTextView() {
        return cardTitleTextView;
    }

    public void setViewAllButtonClickListener(OnClickListener listener) {
        this.viewAllButtonClickListener = listener;
    }
}

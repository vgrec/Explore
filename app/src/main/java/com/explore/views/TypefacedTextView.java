package com.explore.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.vgrec.explore.R;

/**
 * Custom TextView used to display custom fonts.
 *
 * @author vgrec, created on 1/29/15.
 */
public class TypefacedTextView extends AppCompatTextView {

    public static String DEFAULT_FONT = "fonts/Roboto-Medium.ttf";

    public TypefacedTextView(Context context) {
        this(context, null);
    }

    public TypefacedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypefacedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_textviewFont);
        styledAttrs.recycle();
        setTypeface(Typeface.createFromAsset(context.getAssets(), fontName == null ? DEFAULT_FONT : "fonts/" + fontName + ".ttf"));
    }
}

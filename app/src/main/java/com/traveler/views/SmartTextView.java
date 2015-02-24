package com.traveler.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * A TextView that shows itself only if the supplied text is not null and not empty.
 * Otherwise its visibility is set as GONE.
 *
 * @author vgrec, created on 2/24/15.
 */
public class SmartTextView extends TextView {

    public SmartTextView(Context context) {
        super(context);
    }

    public SmartTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text) {
        if (text != null && text.length() > 0) {
            super.setText(text);
        } else {
            setVisibility(View.GONE);
        }
    }
}

package com.explore.listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Class that provides empty implementations for {@link TextWatcher} methods,
 * so that you can override only the methods of interest.
 *
 * @author vgrec, created on 8/22/14.
 */
public class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

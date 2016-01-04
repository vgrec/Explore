package com.explore.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vgrec.explore.R;
import com.explore.models.wikipedia.PageDescription;

/**
 * @author vgrec, created on 10/27/14.
 */
public class DescriptionFragment extends Fragment {

    private PageDescription pageDescription;

    public DescriptionFragment() {
    }

    public static Fragment newInstance(Bundle extras) {
        DescriptionFragment fragment = new DescriptionFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            pageDescription = (PageDescription) getArguments().getSerializable(LocationSummaryFragment.KEY_PAGE_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
        if (pageDescription != null) {
            descriptionTextView.setText(pageDescription.getExtract());
        }
        return view;
    }
}

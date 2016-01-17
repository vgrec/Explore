package com.explore.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.explore.http.ExploreHttpFacade;
import com.explore.http.ExploreHttpFacadeImpl;

import java.util.ArrayList;

/**
 * Adapter used in conjunction with {@link android.widget.AutoCompleteTextView} to
 * show completion suggestions while the user is typing.
 *
 * @author vgrec, created on 3/3/15.
 */
public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> resultList;
    private ExploreHttpFacade ioFacade;

    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        ioFacade = new ExploreHttpFacadeImpl(context);
    }

    @Override
    public int getCount() {
        return resultList != null ? resultList.size() : 0;
    }

    @Override
    public String getItem(int position) {
        return resultList != null ? resultList.get(position) : null;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = ioFacade.autocomplete(constraint.toString());
                    if (resultList != null) {
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}

package com.explore.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore.Constants;
import com.explore.models.db.SavedPlace;
import com.explore.views.PreviewImage;
import com.vgrec.explore.R;

import java.util.List;

/**
 * @author vgrec, created on 1/23/15.
 */
public class SavedPlacesAdapter extends RecyclerView.Adapter<SavedPlacesAdapter.ViewHolder> {

    private List<SavedPlace> savedPlaces;

    public SavedPlacesAdapter(List<SavedPlace> savedPlaces) {
        this.savedPlaces = savedPlaces;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SavedPlace savedPlace = savedPlaces.get(position);
        holder.previewImage.setUrl(String.format(Constants.Google.IMAGE_URL, savedPlace.getImageUrl()));
        holder.previewImage.setText(savedPlace.getTitle());
    }

    @Override
    public int getItemCount() {
        return savedPlaces.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PreviewImage previewImage;

        public ViewHolder(View itemView) {
            super(itemView);
            previewImage = (PreviewImage) itemView.findViewById(R.id.favorite_place);
        }
    }
}

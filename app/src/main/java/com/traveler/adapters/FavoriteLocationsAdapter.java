package com.traveler.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveler.R;
import com.traveler.models.db.Location;
import com.traveler.views.PreviewImage;

import java.util.List;

/**
 * @author vgrec, created on 1/23/15.
 */
public class FavoriteLocationsAdapter extends RecyclerView.Adapter<FavoriteLocationsAdapter.ViewHolder> {

    private List<Location> locations;

    public FavoriteLocationsAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.previewImage.setUrl(location.getImageUrl());
        holder.previewImage.setText(location.getTitle());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PreviewImage previewImage;

        public ViewHolder(View itemView) {
            super(itemView);
            previewImage = (PreviewImage) itemView.findViewById(R.id.favorite_place);
        }
    }
}

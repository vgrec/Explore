package com.explore.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.explore.http.ImageLoader;
import com.explore.http.Urls;
import com.explore.listeners.OnItemClickListener;
import com.explore.models.google.Place;
import com.vgrec.explore.R;

import java.util.List;

/**
 * Adapter responsible for inflating and populating with data a {@link Place} item
 * displayed in RecyclerView.
 *
 * @author vgrec, created on 11/4/14.
 */
public class PlaceItemAdapter extends RecyclerView.Adapter<PlaceItemAdapter.ViewHolder> {

    private List<Place> places;
    private Context context;
    private boolean taskInProgress;
    private OnLoadMoreListener loadMoreItemsListener;
    private OnItemClickListener<Place> onItemClickListener;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public PlaceItemAdapter(List<Place> places) {
        this.places = places;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Place place = places.get(position);
        viewHolder.name.setText(place.getName());

        if (place.getRating() != null) {
            viewHolder.ratingBar.setVisibility(View.VISIBLE);
            viewHolder.ratingBar.setVisibility(View.VISIBLE);
            viewHolder.ratingNumber.setText(place.getRating());
            viewHolder.ratingBar.setRating(Float.valueOf(place.getRating()));
        } else {
            viewHolder.ratingBar.setVisibility(View.GONE);
            viewHolder.ratingBar.setVisibility(View.GONE);
        }

        boolean isOpened = false;
        if (place.getOpeningHours() != null) {
            isOpened = place.getOpeningHours().isOpened();
        }

        String status = isOpened
                ? context.getString(R.string.status_opened)
                : context.getString(R.string.status_closed);

        int statusColor = isOpened
                ? ContextCompat.getColor(context, R.color.green)
                : ContextCompat.getColor(context, R.color.red);

        viewHolder.openingStatus.setText(status);
        viewHolder.openingStatus.setTextColor(statusColor);

        if (place.getPhotos() != null && place.getPhotos().size() > 0) {
            String url = Urls.getPlaceThumbnailUrl(place.getPhotos().get(0).getPhotoReference());
            ImageLoader.loadImage(context, url, viewHolder.placePicture);
        } else {
            ImageLoader.loadImage(context, place.getIconUrl(), viewHolder.placePicture);
        }

        loadMoreIfLastRowHit(position);
    }

    private void loadMoreIfLastRowHit(int position) {
        boolean lastRowHit = (position == places.size() - 1);
        if (lastRowHit && !taskInProgress) {
            if (loadMoreItemsListener != null) {
                loadMoreItemsListener.onLoadMore();
            }
        }
    }

    public void setTaskInProgress(boolean taskInProgress) {
        this.taskInProgress = taskInProgress;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView ratingNumber;
        RatingBar ratingBar;
        NetworkImageView placePicture;
        TextView openingStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ratingNumber = itemView.findViewById(R.id.rating);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            placePicture = itemView.findViewById(R.id.place_picture);
            openingStatus = itemView.findViewById(R.id.opening_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(places.get(getAdapterPosition()));
            }
        }
    }

    public void setLoadMoreItemListener(OnLoadMoreListener listener) {
        this.loadMoreItemsListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener<Place> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

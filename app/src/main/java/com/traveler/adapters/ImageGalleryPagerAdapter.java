package com.traveler.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.toolbox.NetworkImageView;
import com.traveler.Constants;
import com.traveler.ImageHelper;
import com.traveler.R;
import com.traveler.Size;
import com.traveler.models.flickr.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vgrec, created on 10/24/14.
 */
public class ImageGalleryPagerAdapter extends PagerAdapter {

    private List<Photo> photos = new ArrayList<Photo>();
    private LayoutInflater inflater;
    private Context context;

    public ImageGalleryPagerAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos.addAll(photos);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_image, container, false);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        final NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.image);
        Photo photo = photos.get(position);
        String url = String.format(Constants.Flickr.PHOTO_URL, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), Size.z);
        ImageHelper.loadImage(context, url, imageView);
        imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                // the layout of the logo view changes at least twice: When it is added
                // to the parent and when the image has been loaded. We are only interested
                // in the second case and to find that case, we do this if statement

                if (imageView.getDrawable() != null) {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
         return view.equals(o);
    }
}

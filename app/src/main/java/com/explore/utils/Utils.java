package com.explore.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.explore.Constants;
import com.explore.http.Urls;
import com.explore.models.flickr.FlickrPhoto;
import com.explore.models.flickr.Size;
import com.explore.models.google.GooglePhoto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides different utility methods.
 *
 * @author vgrec, created on 9/5/14.
 */
public class Utils {

    public static <T> T fromJson(Class<T> clazz, String json) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            Log.e(Constants.TAG, "Cannot construct object from json", e);
        }
        return null;
    }

    public static void setColorForTextViewDrawable(int color, TextView... textViews) {
        for (TextView textView : textViews) {
            Drawable[] drawables = textView.getCompoundDrawables();
            Drawable leftDrawable = drawables[0];
            leftDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }


    public static ArrayList<String> flickrPhotosToUrls(ArrayList<FlickrPhoto> flickrPhotos) {
        ArrayList<String> urls = new ArrayList<>();
        for (FlickrPhoto flickrPhoto : flickrPhotos) {
            urls.add(Urls.getFlickrImageUrl(flickrPhoto, Size.z));
        }
        return urls;
    }

    public static ArrayList<String> googlePhotosToUrls(List<GooglePhoto> photos) {
        ArrayList<String> urls = new ArrayList<>();
        for (GooglePhoto photo : photos) {
            urls.add(Urls.getPlaceImageUrl(photo.getPhotoReference()));
        }
        return urls;
    }

    public static String encodeAsUrl(String input) {
        try {
            return URLEncoder.encode(input, "utf8");
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    public static String parseDuration(String duration) {
        String formattedDuration = "";

        try {
            PeriodFormatter formatter = ISOPeriodFormat.standard();
            Period period = formatter.parsePeriod(duration);
            int hours = period.getHours();
            int minutes = period.getMinutes();
            int seconds = period.getSeconds();

            String secondsStr = format(seconds);
            String minutesStr = format(minutes);
            String hoursStr = format(hours);

            formattedDuration = minutesStr + ":" + secondsStr;

            if (hours > 0) {
                formattedDuration = hoursStr + ":" + formattedDuration;
            }

        } catch (IllegalArgumentException e) {
            Log.e(Constants.TAG, "Cannot parse video duration", e);
        }

        return formattedDuration;
    }

    /**
     * @param time an integer that represents seconds, minutes, or hours
     */
    @NonNull
    private static String format(int time) {
        String secondsStr = String.valueOf(time);
        if (time < 10) {
            secondsStr = "0" + time;
        }
        return secondsStr;
    }
}

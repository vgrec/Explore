package com.traveler.utils;

import android.text.Html;

import com.traveler.models.youtube.Entry;
import com.traveler.models.youtube.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vgrec, created on 12/3/14.
 */
public class VideoUtils {

    public static List<Video> toVideos(List<Entry> entries) {
        List<Video> videos = new ArrayList<Video>();
        for (Entry entry : entries) {
            String title = entry.getTitle().getText();
            String thumbnail = extractThumbnail(entry.getContent().getText());
            String duration = extractDuration(Html.fromHtml(entry.getContent().getText()).toString());
            String link = entry.getLinks().get(0).getHref();

            videos.add(new Video(title, thumbnail, duration, link));
        }

        return videos;
    }

    private static String extractThumbnail(String text) {
        final String SRC_TAG = "src";
        final String IMAGE_EXTENTION = ".jpg";

        int srcIndex = text.indexOf(SRC_TAG);
        if (srcIndex != -1) {
            int imageIndex = text.indexOf(IMAGE_EXTENTION, srcIndex);
            if (imageIndex != -1) {
                return text.substring(srcIndex + SRC_TAG.length() + 2, imageIndex + IMAGE_EXTENTION.length());
            }
        }

        return null; // couldn't parse
    }

    private static String extractDuration(String text) {
        String result = "";

        final String TIME_TAG = "Time: ";
        int timeIndex = text.indexOf(TIME_TAG);
        if (timeIndex != -1) {
            String theRest = text.substring(timeIndex + TIME_TAG.length());
            for (int i = 0; i < theRest.length(); i++) {
                if (Character.isDigit(theRest.charAt(i)) || theRest.charAt(i) == ':') {
                    result += String.valueOf(theRest.charAt(i));
                } else {
                    break;
                }
            }
        }

        return result;
    }
}

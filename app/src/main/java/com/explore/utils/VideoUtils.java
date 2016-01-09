package com.explore.utils;

import com.explore.models.youtube.ContentDetails;
import com.explore.models.youtube.SearchItem;
import com.explore.models.youtube.ResourceId;
import com.explore.models.youtube.Video;
import com.explore.models.youtube.VideoDetailsResponse;
import com.explore.models.youtube.VideoSearchResponse;
import com.explore.models.youtube.VideosResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that provides useful helper methods in the context
 * of processing the video search results.
 *
 * @author vgrec, created on 12/3/14.
 */
public class VideoUtils {


    public static String buildIdsString(List<SearchItem> searchItems) {
        // If the list contains only 1 item, then we return only the single videoId of that item.
        if (searchItems.size() == 1) {
            return searchItems.get(0).getResourceId().getVideoId();
        }

        // Otherwise we build a long string from all the videoId's separated by a comma ','.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < searchItems.size() - 1; i++) {
            sb.append(searchItems.get(i).getResourceId().getVideoId()).append(",");
        }
        sb.append(searchItems.get(searchItems.size() - 1).getResourceId().getVideoId());

        return sb.toString();
    }

    public static VideosResponse join(VideoSearchResponse videosResponse, VideoDetailsResponse detailsResponse) {
        List<Video> videos = new ArrayList<>();

        for (int i = 0; i < videosResponse.getSize(); i++) {
            SearchItem searchItem = videosResponse.getItems().get(i);
            ResourceId resourceId = searchItem.getResourceId();
            ContentDetails details = detailsResponse.getItems().get(i).getContentDetails();

            final String TYPE_VIDEO = "youtube#video";
            if (resourceId.getKind().equals(TYPE_VIDEO)) {
                String title = searchItem.getSnippet().getTitle();
                String thumbnailUrl = searchItem.getSnippet().getThumbnails().getMediumUrl();
                String duration = details.getDuration();
                String videoId = resourceId.getVideoId();
                videos.add(new Video(title, thumbnailUrl, duration, videoId));
            }

        }

        return new VideosResponse(videos);
    }
}

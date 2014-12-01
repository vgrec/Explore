package com.traveler.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveler.R;
import com.traveler.http.TaskFinishedListener;
import com.traveler.http.TravelerIoFacade;
import com.traveler.http.TravelerIoFacadeImpl;
import com.traveler.models.youtube.Entry;
import com.traveler.models.youtube.VideosResponse;

import java.util.List;

public class VideosFragment extends Fragment {

    public VideosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity());
        ioFacade.getVideos(new TaskFinishedListener<VideosResponse>() {
            @Override
            protected void onSuccess(VideosResponse result) {
                if (result != null) {
                    List<Entry> entries = result.getFeed().getEntries();
                    for (Entry entry : entries) {
                        Log.d("GREC", entry.getTitle().getText());
                        Log.d("GREC", entry.getContent().getText());
                    }
                }
            }
        });
    }
}

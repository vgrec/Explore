package com.traveler.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveler.R;
import com.traveler.adapters.VideosAdapter;
import com.traveler.http.TaskFinishedListener;
import com.traveler.http.TravelerIoFacade;
import com.traveler.http.TravelerIoFacadeImpl;
import com.traveler.listeners.RecyclerItemClickListener;
import com.traveler.models.youtube.Entry;
import com.traveler.models.youtube.Video;
import com.traveler.models.youtube.VideosResponse;
import com.traveler.utils.VideoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VideosFragment extends Fragment {

    public VideosFragment() {
    }

    private VideosAdapter adapter;
    private List<Video> videos;

    @InjectView(R.id.videos_list)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            videos = new ArrayList<Video>();
            adapter = new VideosAdapter(videos);
            downloadVideos();
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        openPlayVideoActivity(position);
                    }
                })
        );
        recyclerView.setAdapter(adapter);
    }

    private void downloadVideos() {
        TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity());
        ioFacade.getVideos(new TaskFinishedListener<VideosResponse>() {
            @Override
            protected void onSuccess(VideosResponse result) {
                if (result != null) {
                    List<Entry> entries = result.getFeed().getEntries();
                    List<Video> videosResponse = VideoUtils.toVideos(entries);
                    videos.addAll(videosResponse);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void openPlayVideoActivity(int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position).getLink())));
    }
}

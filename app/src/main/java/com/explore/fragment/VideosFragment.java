package com.explore.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.explore.adapters.VideosAdapter;
import com.explore.http.TravelerIoFacade;
import com.explore.http.TravelerIoFacadeImpl;
import com.explore.listeners.OnItemClickListener;
import com.explore.models.events.VideosErrorEvent;
import com.explore.models.youtube.Video;
import com.explore.models.youtube.VideosResponse;
import com.explore.views.ProgressView;
import com.vgrec.explore.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class VideosFragment extends Fragment {

    public VideosFragment() {
    }

    private VideosAdapter adapter;
    private List<Video> videos;

    @InjectView(R.id.videos_list)
    RecyclerView recyclerView;

    @InjectView(R.id.progress_view)
    ProgressView progressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        EventBus.getDefault().register(this);
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

        adapter.setOnItemClickListener(new OnItemClickListener<Video>() {
            @Override
            public void onItemClick(Video item) {
                openPlayVideoActivity(item);
            }
        });
        recyclerView.setAdapter(adapter);

        progressView.setTryAgainClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadVideos();
            }
        });
    }

    private void downloadVideos() {
        progressView.show();
        TravelerIoFacade ioFacade = new TravelerIoFacadeImpl(getActivity());
        ioFacade.getVideos();
    }

    /**
     * Called by {@link EventBus} when the videos are received
     */
    public void onEvent(final VideosResponse result) {
        progressView.hide();
        if (result != null) {
            List<Video> videosResponse = result.getVideos();
            videos.addAll(videosResponse);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Called by {@link EventBus} in case an error occurred during the
     * retrieval of videos.
     */
    public void onEvent(VideosErrorEvent errorEvent) {
        progressView.showError();
    }

    private void openPlayVideoActivity(Video video) {
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(position).getLink())));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

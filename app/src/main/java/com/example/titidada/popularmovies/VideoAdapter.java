package com.example.titidada.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.titidada.popularmovies.model.VideoInfo;
import com.example.titidada.popularmovies.utilities.JsonUtils;

import java.util.List;

import static com.example.titidada.popularmovies.utilities.NetworkUtils.buildTrailerUri;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private int numVideo;
    private List<VideoInfo> videos;
    private Context mContext;


    public VideoAdapter(Context context, String queryResult){
        mContext = context;
        refreshVideos(queryResult);
    }

    public void refreshVideos(String queryResult){
        videos = JsonUtils.parseVideosJson(queryResult);
        numVideo = (videos != null) ? videos.size() : 0;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, final int i) {

        VideoInfo video = videos.get(i);
        videoViewHolder.videoInfoTextView.setText(video.getName());

        videoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri videoUri = buildTrailerUri(videos.get(i).getKey());
                playMedia(videoUri);
            }
        });


    }

    @Override
    public int getItemCount() {
        return numVideo;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView playVideoImage;
        TextView videoInfoTextView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playVideoImage = itemView.findViewById(R.id.play_video);
            videoInfoTextView = itemView.findViewById(R.id.video_info);
        }
    }

    public void playMedia(Uri file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(file);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }


}

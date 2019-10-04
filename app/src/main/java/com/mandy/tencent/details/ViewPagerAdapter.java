package com.mandy.tencent.details;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.mandy.tencent.R;
import com.mandy.tencent.projects.ProjectApis;
import com.mandy.tencent.util.Config;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    ArrayList<ProjectApis.EnquiryVideo> arrayList = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<ProjectApis.EnquiryVideo> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((RelativeLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final VideoView videoView;
        final ImageView imageView;
        final AVLoadingIndicatorView avi;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.coustom_video, container, false);
        videoView = itemView.findViewById(R.id.vidwoView);
        avi = itemView.findViewById(R.id.avi);
        imageView = itemView.findViewById(R.id.imageVideo);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                try {
                    // Start the MediaController
                    MediaController mediacontroller = new MediaController(context);
                    mediacontroller.setAnchorView(videoView);
                    Uri videoUri = Uri.parse(Config.VIDEO_URL + arrayList.get(position).getFile());
                    videoView.setMediaController(mediacontroller);
                    videoView.setVideoURI(videoUri);
                    videoView.seekTo(1);

                } catch (Exception e) {

                    e.printStackTrace();
                }

                videoView.requestFocus();


                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();
                        avi.setVisibility(View.GONE);
                    }
                });


                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {
                        videoView.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        ((ViewPager) container).addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}

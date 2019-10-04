package com.mandy.tencent.projects;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.tencent.R;
import com.mandy.tencent.details.DetailsActivity;
import com.mandy.tencent.util.Config;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    Context context;

    ArrayList<ProjectApis.Datum> arrayList = new ArrayList<>();

    public ProjectAdapter(Context context, ArrayList<ProjectApis.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_project, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ProjectApis.Datum datum = arrayList.get(i);
        viewHolder.txtM2.setText("M2: " + datum.getM2().toString());
        viewHolder.txtRooms.setText("Rooms: " + datum.getRoom().toString());
        viewHolder.txtHeading.setText(datum.getDropdown().toString());
        viewHolder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pos", "" + i);
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(Config.VIDEO_URL + datum.getEnquiryVideo().get(0).getFile().toString())
                .diskCacheStrategy(DiskCacheStrategy.DATA).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                viewHolder.avLoadingIndicatorView.setVisibility(View.GONE);
                return false;
            }
        }).into(viewHolder.roundedImageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;
        TextView txtHeading, txtRooms, txtM2;
        Button btnView;
        AVLoadingIndicatorView avLoadingIndicatorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.imageView);
            txtHeading = itemView.findViewById(R.id.txtHeading);
            txtRooms = itemView.findViewById(R.id.txtRoom);
            txtM2 = itemView.findViewById(R.id.txtM2);
            btnView = itemView.findViewById(R.id.buttonGo);
            avLoadingIndicatorView = itemView.findViewById(R.id.avi);


        }
    }
}

package com.mandy.tencent.uploadimages.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.tencent.R;

import java.util.ArrayList;

public class UploadImageAdapter extends RecyclerView.Adapter<UploadImageAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList = new ArrayList<>();

    public OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick1) {
        onItemClick = onItemClick1;
    }

    public UploadImageAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_images, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(arrayList.get(i), MediaStore.Images.Thumbnails.MINI_KIND);

        viewHolder.roundedImageView.setImageBitmap(thumb);

        viewHolder.roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.roundimageUpload);
        }
    }
}

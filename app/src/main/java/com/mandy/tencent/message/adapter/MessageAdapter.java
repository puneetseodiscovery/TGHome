package com.mandy.tencent.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.tencent.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayMessage = new ArrayList<>();

    public MessageAdapter(Context context, ArrayList<String> arrayMessage) {
        this.context = context;
        this.arrayMessage = arrayMessage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_message, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (arrayMessage.get(i).equalsIgnoreCase("0")) {
            viewHolder.relSend.setVisibility(View.GONE);
            viewHolder.relRecive.setVisibility(View.VISIBLE);
        } else {
            viewHolder.relRecive.setVisibility(View.GONE);
            viewHolder.relSend.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView senderImage, reciverImage;
        TextView senderText, reciverText;
        RelativeLayout relRecive, relSend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            senderImage = itemView.findViewById(R.id.senderImage);
            senderText = itemView.findViewById(R.id.senderMessage);

            reciverImage = itemView.findViewById(R.id.reciverImage);
            reciverText = itemView.findViewById(R.id.reciverMessage);

            relRecive = itemView.findViewById(R.id.relRecieve);
            relSend = itemView.findViewById(R.id.relSend);
        }
    }
}

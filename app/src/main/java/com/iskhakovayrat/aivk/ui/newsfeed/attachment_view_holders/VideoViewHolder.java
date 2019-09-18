package com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iskhakovayrat.aivk.presentation.newsfeed.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public VideoViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void bind(Attachments attachment, OnAttachmentClickListener onAttachmentClickListener) {
        if (attachment.getVideo().getPhoto_800() != null) {
            Glide.with(imageView.getContext())
                    .load(attachment.getVideo().getPhoto_800()).into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(attachment.getVideo().getFirst_frame_800()).into(imageView);
        }
        imageView.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
    }
}

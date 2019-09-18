package com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iskhakovayrat.aivk.presentation.newsfeed.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;
import com.iskhakovayrat.aivk.entity.newsfeed.attachments.PhotoSizes;

public class LinkViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private ImageView icon;
    private TextView title;
    private TextView underTitle;

    public LinkViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        icon = itemView.findViewById(R.id.icon);
        title = itemView.findViewById(R.id.title);
        underTitle = itemView.findViewById(R.id.underTitle);
    }

    public void bind(Attachments attachment, int itemCount, OnAttachmentClickListener onAttachmentClickListener) {
        if (itemCount == 1 && attachment.getLink().getPhoto() != null) {
            PhotoSizes size = attachment.getLink().getPhoto().getNeededSize();
            if (size != null) {
                imageView.setVisibility(View.VISIBLE);
                int finalWidth = imageView.getContext().getResources().getDisplayMetrics().widthPixels;
                int finalHeight = size.getHeight() * finalWidth / size.getWidth();
                imageView.getLayoutParams().width = finalWidth;
                imageView.getLayoutParams().height = finalHeight;
                Glide.with(imageView.getContext())
                        .load(size.getUrl())
                        .apply(new RequestOptions()
                                .override(finalWidth, finalHeight)
                                .placeholder(R.drawable.bg_placeholder)
                                .centerCrop()
                                .dontAnimate())
                        .into(imageView);
            }
        } else {
            imageView.setVisibility(View.GONE);
        }

        icon.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
        title.setText(attachment.getLink().getTitle());
        title.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
        underTitle.setText(attachment.getLink().getUrl());
        underTitle.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
    }
}

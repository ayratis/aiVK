package com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;

public class StickerViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public StickerViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void bind(Attachments attachment) {
        if (attachment.getSticker().getImages().size() > 0) {
            Glide.with(imageView.getContext())
                    .load(attachment.getSticker().getImages().get(1).getUrl())
                    .into(imageView);
        }
    }
}

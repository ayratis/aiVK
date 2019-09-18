package com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iskhakovayrat.aivk.presentation.newsfeed.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;

public class AudioViewHolder extends RecyclerView.ViewHolder {

    private ImageView icon;
    private TextView title;
    private TextView underTitle;

    public AudioViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        underTitle = itemView.findViewById(R.id.underTitle);
        icon = itemView.findViewById(R.id.icon);
    }

    public void bind(Attachments attachment, OnAttachmentClickListener onAttachmentClickListener) {
       title.setText(attachment.getAudio().getArtist());
        underTitle.setText(attachment.getAudio().getTitle());
        icon.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
    }
}

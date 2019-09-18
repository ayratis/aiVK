package com.iskhakovayrat.aivk.ui.newsfeed;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.NewsfeedItems;
import com.iskhakovayrat.aivk.presentation.newsfeed.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.ui.newsfeed.adapters.NewsfeedAttachmentsAdapter;
import com.iskhakovayrat.aivk.utils.DateConverter;

public class NewsfeedViewHolder extends RecyclerView.ViewHolder {
    private ImageView sourceIcon;
    private TextView sourceName;
    private TextView postDate;
    private TextView postTextMain;
    private TextView postLikes;
    private TextView postComments;
    private TextView postReposts;
    private RecyclerView attachmentsRecyclerView;

    public NewsfeedViewHolder(View itemView) {
        super(itemView);
        sourceIcon = itemView.findViewById(R.id.post_group_icon);
        sourceName = itemView.findViewById(R.id.source_group_title);
        postDate = itemView.findViewById(R.id.post_date);
        postTextMain = itemView.findViewById(R.id.post_text_main);

        postLikes = itemView.findViewById(R.id.post_likes);
        postComments = itemView.findViewById(R.id.post_comments);
        postReposts = itemView.findViewById(R.id.post_reposts);
        attachmentsRecyclerView = itemView.findViewById(R.id.universal_recycler_view);
    }

    public void bind(NewsfeedItems item, String sourceIconUrl, String sourceNameText, OnAttachmentClickListener onAttachmentClickListener) {

        Glide.with(sourceIcon.getContext())
                .load(sourceIconUrl)
                .into(sourceIcon);
        sourceName.setText(sourceNameText);
        postDate.setText(DateConverter.getFormatedDate(item.getDate()));

        if (item.getText() == null || item.getText().length() == 0) {
            postTextMain.setVisibility(View.GONE);
        } else {
            postTextMain.setVisibility(View.VISIBLE);
            String text;
            if (item.getText().length() > 200) {
                text = item.getText().substring(0, 199) + "...";
            } else {
                text = item.getText();
            }
            postTextMain.setText(text);
        }

        if (item.getAttachments() != null && item.getAttachments().size() > 0) {
            NewsfeedAttachmentsAdapter adapter = new NewsfeedAttachmentsAdapter(item.getAttachments(), onAttachmentClickListener);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            attachmentsRecyclerView.setLayoutManager(layoutManager);
            attachmentsRecyclerView.setAdapter(adapter);
        }

        postLikes.setText(String.valueOf(item.getLikes().getCount()));
        postComments.setText(String.valueOf(item.getComments().getCount()));
        postReposts.setText(String.valueOf(item.getReposts().getCount()));
    }

}

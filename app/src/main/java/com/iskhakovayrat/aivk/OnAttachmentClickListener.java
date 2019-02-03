package com.iskhakovayrat.aivk;

import com.iskhakovayrat.aivk.retrofit.newsfeed.Attachments;
import com.iskhakovayrat.aivk.retrofit.newsfeed.attachments.Link;
import com.iskhakovayrat.aivk.retrofit.newsfeed.attachments.Video;

public interface OnAttachmentClickListener {

    void onAttachmentClick(Attachments attachment);
}

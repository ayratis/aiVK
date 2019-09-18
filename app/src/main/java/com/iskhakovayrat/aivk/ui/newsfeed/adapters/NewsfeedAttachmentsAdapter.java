package com.iskhakovayrat.aivk.ui.newsfeed.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iskhakovayrat.aivk.presentation.newsfeed.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.AlbumViewHolder;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.AudioViewHolder;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.DocViewHolder;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.EmptyViewHolder;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.LinkViewHolder;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.PhotoViewHolder;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.StickerViewHolder;
import com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders.VideoViewHolder;

import java.util.List;

public class NewsfeedAttachmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NONE = -1;

    private List<Attachments> attachments;
    private OnAttachmentClickListener onAttachmentClickListener;

    public NewsfeedAttachmentsAdapter(List<Attachments> attachments, OnAttachmentClickListener onAttachmentClickListener) {
        this.onAttachmentClickListener = onAttachmentClickListener;
        this.attachments = attachments;
    }

    @Override
    public int getItemCount() {
        return attachments.size();
    }

    @Override
    public int getItemViewType(int position) {

        Attachments attachment = attachments.get(position);
        Attachments.Type type = attachment.getType();

        if (type == Attachments.Type.WALL) {
            if (attachment.getWall().getAttachments() != null && attachment.getWall().getAttachments().size() > 0) {
                type = attachment.getWall().getAttachments().get(0).getType();
            }
        }

        switch (type) {
            case PHOTO:
                return R.layout.item_photo;
            case LINK:
                return R.layout.item_link;
            case VIDEO:
                return R.layout.item_video;
            case DOC:
                return R.layout.item_doc;
            case AUDIO:
                return R.layout.item_audio;
            case ALBUM:
                return R.layout.item_album;
            case STICKER:
                return R.layout.item_sticker;
            default:
                return TYPE_NONE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case R.layout.item_photo:
                return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
            case R.layout.item_link:
                return new LinkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false));
            case R.layout.item_video:
                return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
            case R.layout.item_doc:
                return new DocViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doc, parent, false));
            case R.layout.item_audio:
                return new AudioViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false));
            case R.layout.item_album:
                return new AlbumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false));
            case R.layout.item_sticker:
                return new StickerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false));
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Attachments attachment = attachments.get(position);
        if (attachment.getType() == Attachments.Type.WALL && attachment.getWall().getAttachments().size() > 0) {
            attachment = attachment.getWall().getAttachments().get(0);
        }

        switch (getItemViewType(position)) {
            case R.layout.item_photo:
                ((PhotoViewHolder) holder).bind(attachment);
                break;
            case R.layout.item_link:
                ((LinkViewHolder) holder).bind(attachment, getItemCount(), onAttachmentClickListener);
                break;
            case R.layout.item_video:
                ((VideoViewHolder) holder).bind(attachment, onAttachmentClickListener);
                break;
            case R.layout.item_doc:
                ((DocViewHolder) holder).bind(attachment);
                break;
            case R.layout.item_audio:
                ((AudioViewHolder) holder).bind(attachment, onAttachmentClickListener);
                break;
            case R.layout.item_album:
                ((AlbumViewHolder) holder).bind(attachment);
                break;
            case R.layout.item_sticker:
                ((StickerViewHolder) holder).bind(attachment);
                break;
        }
    }




}

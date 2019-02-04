package com.iskhakovayrat.aivk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iskhakovayrat.aivk.model.newsfeed.Attachments;

import com.iskhakovayrat.aivk.model.newsfeed.attachments.PhotoSizes;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.doc.Sizes;

import java.util.List;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.ViewHolder> {

    private List<Attachments> attachments;
    private OnAttachmentClickListener onAttachmentClickListener;

    public AttachmentsAdapter(List<Attachments> attachments, OnAttachmentClickListener onAttachmentClickListener) {
        this.attachments = attachments;
        this.onAttachmentClickListener = onAttachmentClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.universal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        setAttachment(holder, attachments.get(position));
    }


    @Override
    public int getItemCount() {
        return attachments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView unIcon;
        private TextView unTitleText;
        private ImageView unImage;
        private TextView unTitleUnderText;
        private TextView unCenterIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            unIcon = itemView.findViewById(R.id.un_icon);
            unTitleText = itemView.findViewById(R.id.un_title_text);
            unImage = itemView.findViewById(R.id.un_image);
            unTitleUnderText = itemView.findViewById(R.id.un_title_under_text);
            unCenterIcon = itemView.findViewById(R.id.un_center_icon);
        }
    }

    private void setAttachment(ViewHolder holder, Attachments attachment) {
        if (attachment.getType() != null) {
            switch (attachment.getType()) {
                case PHOTO:
                    setPhoto(holder, attachment);
                    break;
                case LINK:
                    setLink(holder, attachment);
                    break;
                case VIDEO:
                    setVideo(holder, attachment);
                    break;
                case DOC:
                    setDoc(holder, attachment);
                    break;
                case AUDIO:
                    setAudio(holder, attachment);
                    break;
                case ALBUM:
                    setAlbum(holder, attachment);
                    break;
                case STICKER:
                    setSticker(holder, attachment);
                    break;
                case WALL:
                    setWall(holder, attachment);
                    break;
                default:
                    break;
            }
        }
    }

    private void setPhoto(ViewHolder holder, Attachments attachment) {
        holder.unIcon.setVisibility(View.GONE);
        holder.unTitleText.setVisibility(View.GONE);
        holder.unTitleUnderText.setVisibility(View.GONE);
        holder.unCenterIcon.setVisibility(View.GONE);
        PhotoSizes size = getNeededSize(attachment.getPhoto().getSizes());
        if (size != null) {
            holder.unImage.setVisibility(View.VISIBLE);
            int finalWidth = holder.unImage.getContext().getResources().getDisplayMetrics().widthPixels;
            int finalHeight = size.getHeight() * finalWidth / size.getWidth();
            holder.unImage.getLayoutParams().width = finalWidth;
            holder.unImage.getLayoutParams().height = finalHeight;
            Glide.with(holder.unImage.getContext())
                    .load(size.getUrl())
                    .apply(new RequestOptions()
                            .override(finalWidth, finalHeight)
                            .placeholder(R.drawable.bg_placeholder)
                            .centerCrop()
                            .dontAnimate())
                    .into(holder.unImage);
        }
    }

    private void setLink(final ViewHolder holder, Attachments attachment) {
        holder.unCenterIcon.setVisibility(View.GONE);

        if (attachments.size() == 1 && attachment.getLink().getPhoto() != null) {
            PhotoSizes size = getNeededSize(attachment.getLink().getPhoto().getSizes());
            if (size != null) {
                holder.unImage.setVisibility(View.VISIBLE);
                int finalWidth = holder.unImage.getContext().getResources().getDisplayMetrics().widthPixels;
                int finalHeight = size.getHeight() * finalWidth / size.getWidth();
                holder.unImage.getLayoutParams().width = finalWidth;
                holder.unImage.getLayoutParams().height = finalHeight;
                Glide.with(holder.unImage.getContext())
                        .load(size.getUrl())
                        .apply(new RequestOptions()
                                .override(finalWidth, finalHeight)
                                .placeholder(R.drawable.bg_placeholder)
                                .centerCrop()
                                .dontAnimate())
                        .into(holder.unImage);
            }
        } else {
            holder.unImage.setVisibility(View.GONE);
        }

        holder.unIcon.setVisibility(View.VISIBLE);
        holder.unIcon.setImageResource(R.drawable.bt_link);
        holder.unIcon.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));

        holder.unTitleText.setVisibility(View.VISIBLE);
        holder.unTitleText.setText(attachment.getLink().getTitle());
        holder.unTitleText.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));

        holder.unTitleUnderText.setVisibility(View.VISIBLE);
        holder.unTitleUnderText.setText(attachment.getLink().getUrl());
        holder.unTitleUnderText.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
    }

    private void setVideo(final ViewHolder holder, Attachments attachment) {
        holder.unTitleUnderText.setVisibility(View.GONE);
        holder.unTitleText.setVisibility(View.GONE);
        holder.unIcon.setVisibility(View.GONE);
        if (attachment.getVideo().getPhoto_800() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(attachment.getVideo().getPhoto_800()).into(holder.unImage);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(attachment.getVideo().getFirst_frame_800()).into(holder.unImage);
        }
        holder.unCenterIcon.setBackgroundResource(R.drawable.bt_video);
        holder.unCenterIcon.bringToFront();
        holder.unImage.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
    }

    private void setDoc(final ViewHolder holder, Attachments attachment) {
        if (attachment.getDoc().getType() == 3) {
            holder.unIcon.setVisibility(View.GONE);
            holder.unTitleText.setVisibility(View.GONE);
            holder.unTitleUnderText.setVisibility(View.GONE);
            if (attachment.getDoc().getPreview().getPhoto() != null) {
                boolean isX = false;
                List<Sizes> sizes = attachment.getDoc().getPreview().getPhoto().getSizes();
                for (int i = 0; i < sizes.size(); i++) {
                    if (sizes.get(i).getType().equals("x")) {
                        isX = true;
                        holder.unImage.setVisibility(View.VISIBLE);
                        int finalWidth = holder.unImage.getContext().getResources().getDisplayMetrics().widthPixels;
                        int finalHeight = sizes.get(i).getHeight() * finalWidth / sizes.get(i).getWidth();
                        holder.unImage.getLayoutParams().width = finalWidth;
                        holder.unImage.getLayoutParams().height = finalHeight;
                        Glide.with(holder.unImage.getContext())
                                .load(sizes.get(i).getSrc())
                                .apply(new RequestOptions()
                                        .override(finalWidth, finalHeight)
                                        .placeholder(R.drawable.bg_placeholder)
                                        .centerCrop()
                                        .dontAnimate())
                                .into(holder.unImage);
                    }
                }
                if (!isX) {
                    int finalWidth = holder.unImage.getContext().getResources().getDisplayMetrics().widthPixels;
                    int finalHeight = sizes.get(sizes.size() - 1).getHeight() * finalWidth / sizes.get(sizes.size() - 1).getWidth();
                    holder.unImage.getLayoutParams().width = finalWidth;
                    holder.unImage.getLayoutParams().height = finalHeight;
                    Glide.with(holder.unImage.getContext())
                            .load(sizes.get(sizes.size() - 1).getSrc())
                            .apply(new RequestOptions()
                                    .override(finalWidth, finalHeight)
                                    .placeholder(R.drawable.bg_placeholder)
                                    .centerCrop()
                                    .dontAnimate())
                            .into(holder.unImage);
                }
            }

            holder.unCenterIcon.setBackgroundResource(R.drawable.bt_gif);
            holder.unCenterIcon.bringToFront();
            final String gifUri = attachment.getDoc().getUrl();
            holder.unImage.setOnClickListener(view -> {
                holder.unCenterIcon.setVisibility(View.GONE);
                Glide.with(holder.itemView.getContext())
                        .load(gifUri)
                        .into(holder.unImage);
            });
        } else {
            holder.unImage.setVisibility(View.GONE);
            holder.unIcon.setImageResource(R.drawable.ic_doc);
            holder.unTitleText.setVisibility(View.VISIBLE);
            holder.unTitleText.setText(attachment.getDoc().getTitle());
            holder.unTitleUnderText.setVisibility(View.GONE);
        }
    }

    private void setAudio(ViewHolder holder, Attachments attachment) {
        holder.unImage.setVisibility(View.GONE);
        holder.unCenterIcon.setVisibility(View.GONE);
        holder.unIcon.setImageResource(R.drawable.bt_audio);
        holder.unTitleText.setText(attachment.getAudio().getArtist());
        holder.unTitleUnderText.setText(attachment.getAudio().getTitle());
        holder.unTitleUnderText.setVisibility(View.VISIBLE);
        holder.unIcon.setOnClickListener(view -> onAttachmentClickListener.onAttachmentClick(attachment));
    }

    private void setAlbum(ViewHolder holder, Attachments attachment) {
        holder.unIcon.setVisibility(View.GONE);
        holder.unTitleText.setVisibility(View.GONE);
        holder.unTitleUnderText.setVisibility(View.GONE);

        PhotoSizes size = getNeededSize(attachment.getAlbum().getThumb().getSizes());
        if (size != null) {
            holder.unImage.setVisibility(View.VISIBLE);
            int finalWidth = holder.unImage.getContext().getResources().getDisplayMetrics().widthPixels;
            int finalHeight = size.getHeight() * finalWidth / size.getWidth();
            holder.unImage.getLayoutParams().width = finalWidth;
            holder.unImage.getLayoutParams().height = finalHeight;
            Glide.with(holder.unImage.getContext())
                    .load(size.getUrl())
                    .apply(new RequestOptions()
                            .override(finalWidth, finalHeight)
                            .placeholder(R.drawable.bg_placeholder)
                            .centerCrop()
                            .dontAnimate())
                    .into(holder.unImage);
        }

        holder.unCenterIcon.setVisibility(View.VISIBLE);
        String name = "Альбом: " + "\n" + attachment.getAlbum().getTitle() + "\n" + attachment.getAlbum().getSize() + " фотографии";
        holder.unCenterIcon.setText(name);
    }

    private void setSticker(ViewHolder holder, Attachments attachment) {
        holder.unIcon.setVisibility(View.GONE);
        holder.unTitleText.setVisibility(View.GONE);
        holder.unTitleUnderText.setVisibility(View.GONE);
        holder.unCenterIcon.setVisibility(View.GONE);

        holder.unImage.setVisibility(View.VISIBLE);
        Glide.with(holder.itemView.getContext()).load(attachment.getSticker().getImages().get(1).getUrl()).into(holder.unImage);
    }

    private void setWall(ViewHolder holder, Attachments attachment) {
        if (attachment.getWall().getAttachments() != null && attachment.getWall().getAttachments().size() > 0) {
            Attachments newAttachment = attachment.getWall().getAttachments().get(0);
            setAttachment(holder, newAttachment);
        }
    }

    private PhotoSizes getNeededSize(List<PhotoSizes> sizes) {
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("x")) {
                return size;
            }
        }
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("y")) {
                return size;
            }
        }
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("m")) {
                return size;
            }
        }
        for (PhotoSizes size : sizes) {
            if (size.getType().equals("s")) {
                return size;
            }
        }
        return null;
    }

}

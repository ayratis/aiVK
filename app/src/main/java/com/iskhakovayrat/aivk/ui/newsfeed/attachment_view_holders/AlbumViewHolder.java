package com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;
import com.iskhakovayrat.aivk.entity.newsfeed.attachments.PhotoSizes;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView title;

    public AlbumViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void bind(Attachments attachment) {

        PhotoSizes size = attachment.getAlbum().getThumb().getNeededSize();
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

        } else {
            imageView.setVisibility(View.GONE);
        }

        String titleText = String.format(
                title.getContext().getString(R.string.album_title),
                attachment.getAlbum().getTitle(),
                String.valueOf(attachment.getAlbum().getSize())
        );
//        String name = "Альбом: " + "\n" + attachment.getAlbum().getTitle() + "\n" + attachment.getAlbum().getSize() + " фотографии";
        title.setText(titleText);
    }
}

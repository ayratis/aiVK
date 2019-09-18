package com.iskhakovayrat.aivk.ui.newsfeed.attachment_view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Attachments;
import com.iskhakovayrat.aivk.entity.newsfeed.attachments.doc.Sizes;

import java.util.List;

public class DocViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private ImageView icon;

    public DocViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        icon = itemView.findViewById(R.id.centerIcon);
    }

    public void bind(Attachments attachment) {
        if (attachment.getDoc().getType() == 3) {
            imageView.setVisibility(View.VISIBLE);
            icon.setVisibility(View.VISIBLE);
            if (attachment.getDoc().getPreview().getPhoto() != null) {
                boolean isX = false;
                List<Sizes> sizes = attachment.getDoc().getPreview().getPhoto().getSizes();
                for (int i = 0; i < sizes.size(); i++) {
                    if (sizes.get(i).getType().equals("x")) {
                        isX = true;
                        int finalWidth = imageView.getContext().getResources().getDisplayMetrics().widthPixels;
                        int finalHeight = sizes.get(i).getHeight() * finalWidth / sizes.get(i).getWidth();
                        imageView.getLayoutParams().width = finalWidth;
                        imageView.getLayoutParams().height = finalHeight;
                        Glide.with(imageView.getContext())
                                .load(sizes.get(i).getSrc())
                                .apply(new RequestOptions()
                                        .override(finalWidth, finalHeight)
                                        .placeholder(R.drawable.bg_placeholder)
                                        .centerCrop()
                                        .dontAnimate())
                                .into(imageView);
                    }
                }
                if (!isX) {
                    int finalWidth = imageView.getContext().getResources().getDisplayMetrics().widthPixels;
                    int finalHeight = sizes.get(sizes.size() - 1).getHeight() * finalWidth / sizes.get(sizes.size() - 1).getWidth();
                    imageView.getLayoutParams().width = finalWidth;
                    imageView.getLayoutParams().height = finalHeight;
                    Glide.with(imageView.getContext())
                            .load(sizes.get(sizes.size() - 1).getSrc())
                            .apply(new RequestOptions()
                                    .override(finalWidth, finalHeight)
                                    .placeholder(R.drawable.bg_placeholder)
                                    .centerCrop()
                                    .dontAnimate())
                            .into(imageView);
                }
            }

            final String gifUri = attachment.getDoc().getUrl();
            imageView.setOnClickListener(view -> {
                icon.setVisibility(View.GONE);
                Glide.with(imageView.getContext())
                        .load(gifUri)
                        .into(imageView);
            });
        } else {
            imageView.setVisibility(View.GONE);
            icon.setVisibility(View.GONE);
        }

    }
}

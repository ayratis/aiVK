package com.iskhakovayrat.aivk;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iskhakovayrat.aivk.di.Injector;
import com.iskhakovayrat.aivk.main.MainModel;
import com.iskhakovayrat.aivk.model.get_history.FwdMessages;
import com.iskhakovayrat.aivk.model.users.get.UsersGet;
import com.iskhakovayrat.aivk.utils.DateConverter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FwdMessagesAdapter extends RecyclerView.Adapter<FwdMessagesAdapter.ViewHolder> {

    private OnAttachmentClickListener onAttachmentClickListener;
    private List<FwdMessages> items;

    private Api api;
    private TokenHolder tokenHolder;

    @Inject
    MainModel mainModel;


    public FwdMessagesAdapter(List<FwdMessages> items,
                              OnAttachmentClickListener onAttachmentClickListener) {
        Injector.inject(this);
        this.items = items;
        this.onAttachmentClickListener = onAttachmentClickListener;
        this.tokenHolder = mainModel.getTokenHolder();
        this.api = mainModel.getApi();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newsfeed_item_conversation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        long sourcetId = items.get(position).getFromId();

        api.getProfileInfoCall(tokenHolder.getToken(), Api.V, sourcetId, "photo_50").enqueue(new Callback<UsersGet>() {
            @Override
            public void onResponse(Call<UsersGet> call, Response<UsersGet> response) {
                String postIconUrl = response.body().getResponse().get(0).getPhoto_50();
                Glide.with(holder.postGroupIcon.getContext()).load(postIconUrl).into(holder.postGroupIcon);
                String profileName = response.body().getResponse().get(0).getFirst_name()
                        + " " + response.body().getResponse().get(0).getLast_name();
                holder.postGroupTitle.setText("\u27A6" + profileName);
            }

            @Override
            public void onFailure(Call<UsersGet> call, Throwable t) {

            }
        });
        FwdMessages item = items.get(position);
        holder.postDate.setText(DateConverter.getFormatedDate(item.getDate()));

        if (item.getText() == null || item.getText().equals("")) {
            holder.postTextMain.setVisibility(View.GONE);
        } else {
            holder.postTextMain.setVisibility(View.VISIBLE);
            holder.postTextMain.setText(item.getText());
        }

        if (item.getAttachments() != null && !item.getAttachments().isEmpty()) {
            AttachmentsAdapter adapter = new AttachmentsAdapter(item.getAttachments(), onAttachmentClickListener);
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
            holder.unRecyclerView.setLayoutManager(layoutManager);
            holder.unRecyclerView.setAdapter(adapter);
            holder.unRecyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView postGroupIcon;
        private TextView postGroupTitle;
        private TextView postDate;
        private TextView postTextMain;
        private RecyclerView unRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            postGroupIcon = itemView.findViewById(R.id.post_group_icon);
            postGroupTitle = itemView.findViewById(R.id.source_group_title);
            postDate = itemView.findViewById(R.id.post_date);
            postTextMain = itemView.findViewById(R.id.post_text_main);
            unRecyclerView = itemView.findViewById(R.id.universal_recycler_view);
        }
    }


}

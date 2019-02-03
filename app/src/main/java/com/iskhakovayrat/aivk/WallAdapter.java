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
import com.iskhakovayrat.aivk.retrofit.groups.getById.GroupsGetById;
import com.iskhakovayrat.aivk.retrofit.newsfeed.attachments.Wall;
import com.iskhakovayrat.aivk.retrofit.users.get.UsersGet;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class WallAdapter extends RecyclerView.Adapter<WallAdapter.ViewHolder> {

    private OnAttachmentClickListener onAttachmentClickListener;
    private SimpleDateFormat format;
    private Wall item;

    private Api api;
    private TokenHolder tokenHolder;

    public WallAdapter(Wall item, OnAttachmentClickListener onAttachmentClickListener, TokenHolder tokenHolder) {
        this.item = item;
        this.onAttachmentClickListener = onAttachmentClickListener;
        this.tokenHolder = tokenHolder;

        format = new SimpleDateFormat("dd MMM HH:mm");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
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

        long sourcetId = item.getFromId();
        if (sourcetId < 0) {
            sourcetId = Math.abs(sourcetId);
            api.getGroupsInfoCall(tokenHolder.getToken(), Api.V, sourcetId).enqueue(new Callback<GroupsGetById>() {
                @Override
                public void onResponse(Call<GroupsGetById> call, Response<GroupsGetById> response) {
                    String postIconUrl = response.body().getResponse().get(0).getPhoto_50();
                    Glide.with(holder.itemView.getContext()).load(postIconUrl).into(holder.postGroupIcon);
                    String groupName = response.body().getResponse().get(0).getName();
                    holder.postGroupTitle.setText("\u27A6" + groupName);
                }

                @Override
                public void onFailure(Call<GroupsGetById> call, Throwable t) {

                }
            });

        }
        else{
            api.getProfileInfoCall(tokenHolder.getToken(), Api.V, sourcetId, Api.PHOTO_50).enqueue(new Callback<UsersGet>() {
                @Override
                public void onResponse(Call<UsersGet> call, Response<UsersGet> response) {
                    String postIconUrl = response.body().getResponse().get(0).getPhoto_50();
                    Glide.with(holder.itemView.getContext()).load(postIconUrl).into(holder.postGroupIcon);
                    String profileName = response.body().getResponse().get(0).getFirst_name()
                            + " " + response.body().getResponse().get(0).getLast_name();
                    holder.postGroupTitle.setText("\u27A6" + profileName);
                }

                @Override
                public void onFailure(Call<UsersGet> call, Throwable t) {

                }
            });
        }

        Date date = new Date(item.getDate() * 1000);
        String sDate = format.format(date);
        holder.postDate.setText(sDate);

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
        return 1;
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
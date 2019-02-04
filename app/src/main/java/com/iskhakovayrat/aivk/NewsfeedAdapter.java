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
import com.iskhakovayrat.aivk.model.newsfeed.Groups;
import com.iskhakovayrat.aivk.model.newsfeed.NewsfeedItems;
import com.iskhakovayrat.aivk.model.newsfeed.Profiles;
import com.iskhakovayrat.aivk.model.newsfeed.Response;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Wall;
import com.iskhakovayrat.aivk.utils.DateConverter;

import java.util.List;


public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.ViewHolder> {

    private OnAttachmentClickListener onAttachmentClickListener;
    private List<NewsfeedItems> items;
    private List<Groups> groups;
    private List<Profiles> profiles;

    private LoadMore loadMore;


    public NewsfeedAdapter(Response response,
                           OnAttachmentClickListener onAttachmentClickListener,
                           LoadMore loadMore) {
        this.onAttachmentClickListener = onAttachmentClickListener;

        this.loadMore = loadMore;

        items = response.getItems();
        groups = response.getGroups();
        profiles = response.getProfiles();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newsfeed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsfeedItems item = items.get(position);
        long sourcetId = item.getSource_id();
        if(sourcetId < 0){
            Groups sourceGroup = getGroupInfo(sourcetId);
            if(sourceGroup != null) {
                String postIconUrl = sourceGroup.getPhoto_50();
                Glide.with(holder.itemView.getContext()).load(postIconUrl).into(holder.postGroupIcon);
                String groupName = sourceGroup.getName();
                holder.postGroupTitle.setText(groupName);
            }
        }
        else{
            Profiles sourceProfile = getProfileInfo(sourcetId);
            if(sourceProfile != null) {
                String postIconUrl = sourceProfile.getPhoto_50();
                Glide.with(holder.itemView.getContext()).load(postIconUrl).into(holder.postGroupIcon);
                String profileName = sourceProfile.getFirst_name() + " " + sourceProfile.getLast_name();
                holder.postGroupTitle.setText(profileName);
            }
        }

        holder.postDate.setText(DateConverter.getFormatedDate(item.getDate()));

        if(item.getText() == null || item.getText().equals("")){
            holder.postTextMain.setVisibility(View.GONE);
        } else {
            holder.postTextMain.setVisibility(View.VISIBLE);
            String text;
            if(item.getText().length() > 200) {
                text = item.getText().substring(0, 199) + "...";
            } else {
                text = item.getText();
            }
            holder.postTextMain.setText(text);
        }

        if(item.getCopyHistory() != null){
            Wall wall = new Wall();
            wall.setDate(item.getCopyHistory().get(0).getDate());
            wall.setFromId((int) item.getCopyHistory().get(0).getOwnerId());
            wall.setText(item.getCopyHistory().get(0).getText());
            wall.setAttachments(item.getCopyHistory().get(0).getAttachments());
            WallAdapter wallAdapter = new WallAdapter(wall, onAttachmentClickListener);
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
            holder.unRecyclerView.setLayoutManager(layoutManager);
            holder.unRecyclerView.setAdapter(wallAdapter);
            holder.unRecyclerView.getAdapter().notifyDataSetChanged();
        } else {
            if(item.getAttachments() != null){
                AttachmentsAdapter adapter = new AttachmentsAdapter(item.getAttachments(), onAttachmentClickListener);
                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
                holder.unRecyclerView.setLayoutManager(layoutManager);
                holder.unRecyclerView.setAdapter(adapter);
                holder.unRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }

        holder.postLikes.setText(String.valueOf(item.getLikes().getCount()));
        holder.postComments.setText(String.valueOf(item.getComments().getCount()));
        holder.postReposts.setText(String.valueOf(item.getReposts().getCount()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView postGroupIcon;
        private TextView postGroupTitle;
        private TextView postDate;
        private TextView postTextMain;
        private TextView postLikes;
        private TextView postComments;
        private TextView postReposts;
        private RecyclerView unRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            postGroupIcon = itemView.findViewById(R.id.post_group_icon);
            postGroupTitle = itemView.findViewById(R.id.source_group_title);
            postDate = itemView.findViewById(R.id.post_date);
            postTextMain = itemView.findViewById(R.id.post_text_main);

            postLikes = itemView.findViewById(R.id.post_likes);
            postComments = itemView.findViewById(R.id.post_comments);
            postReposts = itemView.findViewById(R.id.post_reposts);
            unRecyclerView = itemView.findViewById(R.id.universal_recycler_view);
        }
    }
    private Groups getGroupInfo(long id){
        if(groups != null && !groups.isEmpty()) {
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == Math.abs(id)) return groups.get(i);
            }
        }
        return null;
    }

    private Profiles getProfileInfo(long id){
        if(profiles != null && !profiles.isEmpty()){
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getId() == Math.abs(id)) return profiles.get(i);
            }
        }
        return null;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();
        if(layoutPosition == getItemCount()-3){
            loadMore.onLoadMore(0);
        }
    }

    public void addPosts(Response response){
        List<NewsfeedItems> newItems = response.getItems();
        List<Groups> newGroups = response.getGroups();
        List<Profiles> newProfiles = response.getProfiles();

        items.addAll(newItems);
        groups.addAll(newGroups);
        profiles.addAll(newProfiles);

        notifyItemInserted(getItemCount()-1);
    }
}

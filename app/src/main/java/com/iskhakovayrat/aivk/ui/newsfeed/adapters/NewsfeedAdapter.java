package com.iskhakovayrat.aivk.ui.newsfeed.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iskhakovayrat.aivk.presentation.newsfeed.LoadMoreListener;
import com.iskhakovayrat.aivk.presentation.newsfeed.OnAttachmentClickListener;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.entity.newsfeed.Groups;
import com.iskhakovayrat.aivk.entity.newsfeed.NewsfeedItems;
import com.iskhakovayrat.aivk.entity.newsfeed.Profiles;
import com.iskhakovayrat.aivk.ui.newsfeed.NewsfeedViewHolder;

import java.util.ArrayList;
import java.util.List;


public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedViewHolder> {

    private OnAttachmentClickListener onAttachmentClickListener;
    private List<NewsfeedItems> items = new ArrayList<>();
    private List<Groups> groups;
    private List<Profiles> profiles;

    private LoadMoreListener loadMore;

    public NewsfeedAdapter(OnAttachmentClickListener onAttachmentClickListener,
                           LoadMoreListener loadMore) {
        this.onAttachmentClickListener = onAttachmentClickListener;

        this.loadMore = loadMore;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public NewsfeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newsfeed, parent, false);
        return new NewsfeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsfeedViewHolder holder, int position) {
        NewsfeedItems item = items.get(position);
        long sourceId = item.getSource_id();
        String sourceIconUrl = null;
        String sourceName = null;
        if (sourceId < 0) {
            Groups sourceGroup = getGroupInfo(sourceId);
            if (sourceGroup != null) {
                sourceIconUrl = sourceGroup.getPhoto_50();
                sourceName = sourceGroup.getName();
            }
        } else {
            Profiles sourceProfile = getProfileInfo(sourceId);
            if (sourceProfile != null) {
                sourceIconUrl = sourceProfile.getPhoto_50();
                sourceName = sourceProfile.getFirst_name() + " " + sourceProfile.getLast_name();
            }
        }
        holder.bind(item, sourceIconUrl, sourceName, onAttachmentClickListener);
    }


    private Groups getGroupInfo(long id) {
        if (groups != null && !groups.isEmpty()) {
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == Math.abs(id)) return groups.get(i);
            }
        }
        return null;
    }

    private Profiles getProfileInfo(long id) {
        if (profiles != null && !profiles.isEmpty()) {
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getId() == Math.abs(id)) return profiles.get(i);
            }
        }
        return null;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull NewsfeedViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();
        if (layoutPosition == getItemCount() - 3) {
            loadMore.loadMore(0);
        }
    }

    public void setData(List<NewsfeedItems> data, List<Groups> groups, List<Profiles> profiles) {
        this.groups = groups;
        this.profiles = profiles;
        List<NewsfeedItems> oldItems = new ArrayList<>(items);
        items.clear();
        items.addAll(data);

        DiffUtil.calculateDiff(new DiffCallback(items, oldItems)).dispatchUpdatesTo(this);
    }

    class DiffCallback extends DiffUtil.Callback {

        private List<NewsfeedItems> newList;
        private List<NewsfeedItems> oldList;

        public DiffCallback(List<NewsfeedItems> newList, List<NewsfeedItems> oldList) {
            this.newList = newList;
            this.oldList = oldList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getPostId() == newList.get(newItemPosition).getPostId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getText().equals(newList.get(newItemPosition).getText());
        }
    }
}

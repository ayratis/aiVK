package com.iskhakovayrat.aivk;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iskhakovayrat.aivk.model.get_conversation.Conversation;
import com.iskhakovayrat.aivk.model.get_conversation.LastMessage;
import com.iskhakovayrat.aivk.model.get_conversation.Peer;
import com.iskhakovayrat.aivk.model.get_conversation.Profiles;
import com.iskhakovayrat.aivk.model.get_history.ConversationHistory;
import com.iskhakovayrat.aivk.model.get_history.ConversationHistoryResponse;
import com.iskhakovayrat.aivk.model.newsfeed.Attachments;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConversationRecyclerAdapter extends RecyclerView.Adapter<ConversationRecyclerAdapter.ViewHolder> {

    private List<LastMessage> items;
    private List<Profiles> profiles;
    private Peer peer;
    private LoadMore loadMore;
    private OnAttachmentClickListener onAttachmentClickListener;
    private int messageCount;
    private SimpleDateFormat format;
    private Conversation conversation;


    public ConversationRecyclerAdapter(ConversationHistoryResponse conversationHistoryResponse,
                                       OnAttachmentClickListener onAttachmentClickListener,
                                       LoadMore loadMore) {
        items = conversationHistoryResponse.getItems();
        profiles = conversationHistoryResponse.getProfiles();
        peer = conversationHistoryResponse.getConversations().get(0).getPeer();
        this.loadMore = loadMore;
        this.onAttachmentClickListener = onAttachmentClickListener;
        messageCount = conversationHistoryResponse.getCount();
        format = new SimpleDateFormat("dd MMM HH:mm");
        conversation = conversationHistoryResponse.getConversations().get(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (items.get(position).getAction() != null) {
            holder.msgText.setVisibility(View.VISIBLE);
            holder.msgText.setTextColor(holder.msgText.getContext().getColor(R.color.main_blue));
            switch (items.get(position).getAction().getType()) {
                case CHAT_CREATE:
                    holder.msgText.setText("Создана беседа");
                    break;
                case CHAT_KICK_USER:
                    holder.msgText.setText("Пользователь покинул беседу");
                    break;
                case CHAT_INVITE_USER:
                    holder.msgText.setText("Приглашен пользователь");
                    break;
                case CHAT_PIN_MESSAGE:
                    holder.msgText.setText("Закреплено сообщение");
                    break;
                case CHAT_PHOTO_REMOVE:
                    holder.msgText.setText("Удалена фотография беседы");
                    break;
                case CHAT_PHOTO_UPDATE:
                    holder.msgText.setText("Обновлена фотография беседы");
                    break;
                case CHAT_TITLE_UPDATE:
                    holder.msgText.setText("Обновлено название беседы");
                    break;
                case CHAT_UNPIN_MESSAGE:
                    holder.msgText.setText("Откреплено сообщение");
                    break;
                case CHAT_INVITE_USER_BY_LINK:
                    holder.msgText.setText("Пользователь присоединился к беседе по ссылке");
                    break;
                default:
                    break;
            }
        } else if (items.get(position).getText() != null || !items.get(position).getText().equals("")) {
            holder.msgText.setVisibility(View.VISIBLE);
            holder.msgText.setTextColor(holder.msgText.getContext().getColor(R.color.black));
            holder.msgText.setText(items.get(position).getText());
        } else {
            holder.msgText.setVisibility(View.GONE);
        }

        if (items.get(position).getFwdMessages() != null && !items.get(position).getFwdMessages().isEmpty()) {
            holder.attachmentsRecycler.setVisibility(View.VISIBLE);
            FwdMessagesAdapter fwdMessagesAdapter =
                    new FwdMessagesAdapter(items.get(position).getFwdMessages(), onAttachmentClickListener);
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
            holder.attachmentsRecycler.setLayoutManager(layoutManager);
            holder.attachmentsRecycler.setAdapter(fwdMessagesAdapter);
            holder.attachmentsRecycler.getAdapter().notifyDataSetChanged();
        } else if (items.get(position).getAttachments() != null && items.get(position).getAttachments().size() > 0) {
            if (items.get(position).getAttachments().get(0).getType().equals(Attachments.Type.WALL)) {
                holder.attachmentsRecycler.setVisibility(View.VISIBLE);
                WallAdapter wallAdapter = new WallAdapter(items.get(position).getAttachments().get(0).getWall(),
                        onAttachmentClickListener);
                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
                holder.attachmentsRecycler.setLayoutManager(layoutManager);
                holder.attachmentsRecycler.setAdapter(wallAdapter);
                holder.attachmentsRecycler.getAdapter().notifyDataSetChanged();
            } else {
                holder.attachmentsRecycler.setVisibility(View.VISIBLE);
                AttachmentsAdapterConversation adapter = new AttachmentsAdapterConversation(items.get(position).getAttachments(), onAttachmentClickListener);
                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());

                holder.attachmentsRecycler.setLayoutManager(layoutManager);
                holder.attachmentsRecycler.setAdapter(adapter);
                holder.attachmentsRecycler.getAdapter().notifyDataSetChanged();
            }
        } else {
            holder.attachmentsRecycler.setVisibility(View.GONE);
        }
        Date date = new Date(items.get(position).getDate() * 1000);
        holder.msgDate.setText(format.format(date));

        if(peer.getType().equals("chat")){
            holder.convIcon.setVisibility(View.VISIBLE);
            String chatUserIconUrl = getProfileInfo(items.get(position).getFrom_id()).getPhoto_50();
            Glide.with(holder.itemView.getContext()).load(chatUserIconUrl).into(holder.convIcon);
        }

        if (items.get(position).getOut() == 0) {
            holder.convIcon.setVisibility(View.VISIBLE);
            holder.llbg.setBackground(holder.itemView.getContext().getDrawable(R.drawable.bg_messsage_in));
            holder.llbg.setGravity(Gravity.START);
            holder.msgLayout.setGravity(Gravity.START | Gravity.LEFT);

            holder.unreadOutboxIcon.setVisibility(View.GONE);
        } else if (items.get(position).getOut() == 1) {
            holder.llbg.setBackground(holder.itemView.getContext().getDrawable(R.drawable.bg_message_out));
            holder.llbg.setGravity(Gravity.END);
            holder.msgLayout.setGravity(Gravity.END | Gravity.RIGHT);
            holder.convIcon.setVisibility(View.GONE);

            if (conversation.getOut_read() < items.get(position).getId()) {
                holder.unreadOutboxIcon.setVisibility(View.VISIBLE);
            } else {
                holder.unreadOutboxIcon.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView convIcon;
        private TextView msgText;
        private RecyclerView attachmentsRecycler;
        private RelativeLayout msgLayout;
        private LinearLayout llbg;
        private TextView msgDate;
        private ImageView unreadOutboxIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            convIcon = itemView.findViewById(R.id.conversationItemInIcon);
            msgText = itemView.findViewById(R.id.conversationItemMessageText);
            attachmentsRecycler = itemView.findViewById(R.id.conversationItemAttachmentsRecyclerView);
            msgLayout = itemView.findViewById(R.id.msg_layout);
            llbg = itemView.findViewById(R.id.conversationItemMessageBGLayout);
            msgDate = itemView.findViewById(R.id.conversationItemMessageDate);
            unreadOutboxIcon = itemView.findViewById(R.id.conversationItemUnreadOutboxIcon);

            if (peer.getType().equals("user")) {
                for (int i = 0; i < profiles.size(); i++) {
                    if (peer.getId() == profiles.get(i).getId()) {
                        Glide.with(itemView.getContext()).load(profiles.get(i).getPhoto_50()).into(convIcon);
                    }
                }

            }
        }
    }

    private Profiles getProfileInfo(int id) {
        for (int i = 0; i < profiles.size(); i++) {
            if (id == profiles.get(i).getId()) {
                return profiles.get(i);
            }
        }
        return null;
    }

    public void addMessage(LastMessage item) {

        int id = item.getId();
        for (int i = getItemCount() - 1; i >= 0; i--) {
            if (id == items.get(i).getId()) {
                return;
            }
        }
        items.add(0, item);
        notifyItemInserted(0);
//        notifyDataSetChanged();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        int layoutPosition = holder.getLayoutPosition();
        if (holder.getLayoutPosition() == (getItemCount() - 1) && holder.getLayoutPosition() < messageCount - 1) {
            loadMore.onLoadMore(items.get(getItemCount() - 1).getId());
        }
    }

    public void addConversationHistory(ConversationHistory conversationHistory) {
        List<LastMessage> newItems = conversationHistory.getResponse().getItems();
        newItems.remove(0);
        items.addAll(newItems);
        notifyItemInserted(getItemCount() - 1);
    }

    public void setOutboxReaded(int messageId) {
        conversation.setOut_read(messageId);

        notifyDataSetChanged();
    }
}

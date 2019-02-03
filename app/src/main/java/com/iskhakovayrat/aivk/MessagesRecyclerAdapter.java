package com.iskhakovayrat.aivk;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iskhakovayrat.aivk.retrofit.get_conversation.ConversationGet;
import com.iskhakovayrat.aivk.retrofit.get_conversation.ConversationResponse;
import com.iskhakovayrat.aivk.retrofit.get_conversation.Groups;
import com.iskhakovayrat.aivk.retrofit.get_conversation.Items;
import com.iskhakovayrat.aivk.retrofit.get_conversation.LastMessage;
import com.iskhakovayrat.aivk.retrofit.get_conversation.Profiles;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {
    private List<Items> items;
    private List<Profiles> profiles;
    private List<Groups> groups;
    private OnDialogClickListener onDialogClickListener;
    private SimpleDateFormat format;
    private String myIconUrl;
    private LoadMore loadMore;
    private int conversationsCount;

    public MessagesRecyclerAdapter(ConversationResponse conversationResponse, OnDialogClickListener onDialogClickListener, LoadMore loadMore) {
        items = conversationResponse.getItems();
        profiles = conversationResponse.getProfiles();
        groups = conversationResponse.getGroups();
        this.onDialogClickListener = onDialogClickListener;
        format = new SimpleDateFormat("dd MMM HH:mm");
        myIconUrl = getMyIconUrl();
        this.loadMore = loadMore;
        conversationsCount = conversationResponse.getCount();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_item, parent, false);
        return new ViewHolder(view);
    }

    private String getMyIconUrl() {
        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getId() == 17461467) {
                return profiles.get(i).getPhoto_50();
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (items.get(position).getConversation().getPeer().getType().equals("user")) {
            int userId = items.get(position).getConversation().getPeer().getId();
            Profiles profile = getProfileInfo(userId);
            if (profile != null) {
                String username = profile.getFirst_name() + " " + profile.getLast_name();
                holder.conversationTitle.setText(username);
                String userIcon = profile.getPhoto_50();
                Glide.with(holder.itemView.getContext()).load(userIcon).into(holder.conversationIcon);
                if(profile.getOnline_mobile() == 1){
                    holder.onlineIcon.setVisibility(View.VISIBLE);
                    holder.onlineIcon.setBackgroundResource(R.color.linkText);
                    holder.onlineIcon.setImageResource(R.drawable.ic_online_phone);
                } else if(profile.getOnline() == 1){
                    holder.onlineIcon.setVisibility(View.VISIBLE);
                    holder.onlineIcon.setBackgroundResource(R.drawable.ic_online_web);
                } else {
                    holder.onlineIcon.setVisibility(View.GONE);
                }
            }
        } else if (items.get(position).getConversation().getPeer().getType().equals("chat")) {
            String chatName = items.get(position).getConversation().getChat_setting().getTitle();
            holder.conversationTitle.setText(chatName);
            holder.onlineIcon.setVisibility(View.GONE);
            holder.conversationIcon.setImageResource(R.drawable.im_chat);
        } else if (items.get(position).getConversation().getPeer().getType().equals("group")) {
            int id = items.get(position).getConversation().getPeer().getId();
            holder.onlineIcon.setVisibility(View.GONE);
            Groups group = getGroupInfo(id);
            if (group != null) {
                holder.conversationTitle.setText(group.getName());
                String groupIcon = group.getPhoto_50();
                Glide.with(holder.itemView.getContext()).load(groupIcon).into(holder.conversationIcon);
            }
        }
        Date date = new Date(items.get(position).getLast_message().getDate() * 1000);
        String sDate = format.format(date);
        holder.conversationLastMsgDate.setText(sDate);
        holder.conversationLastMsgText.setText(items.get(position).getLast_message().getText());
        holder.conversationLastMsgText.setTextColor(holder.itemView.getContext().getColor(R.color.default_text));

        if (items.get(position).getLast_message().getAttachments() != null && !items.get(position).getLast_message().getAttachments().isEmpty()) {
            if (items.get(position).getLast_message().getAttachments().size() > 0 && items.get(position).getLast_message().getText().equals("")) {
                holder.conversationLastMsgText.setTextColor(holder.itemView.getContext().getColor(R.color.main_blue));

                if (items.get(position).getLast_message().getAttachments().get(0).getType() != null) {
                    switch (items.get(position).getLast_message().getAttachments().get(0).getType()) {
                        case DOC:
                            if(items.get(position).getLast_message().getAttachments().get(0).getDoc().getType() == 5){
                                holder.conversationLastMsgText.setText("Голосовое сообщение");
                                break;
                            } else {
                                holder.conversationLastMsgText.setText("Документ");
                                break;
                            }
                        case LINK:
                            holder.conversationLastMsgText.setText("Ссылка");
                            break;
                        case AUDIO:
                            holder.conversationLastMsgText.setText("Аудиозапись");
                            break;
                        case PHOTO:
                            holder.conversationLastMsgText.setText("Фотография");
                            break;
                        case VIDEO:
                            holder.conversationLastMsgText.setText("Видеозапись");
                            break;
                        case STICKER:
                            holder.conversationLastMsgText.setText("Стикер");
                            break;
                        case ALBUM:
                            holder.conversationLastMsgText.setText("Альбом");
                            break;
                        case WALL:
                            holder.conversationLastMsgText.setText("Запись");
                            break;
                        default:
                            break;
                    }
                }
            }
        } else if (items.get(position).getLast_message().getFwdMessages() != null && !items.get(position).getLast_message().getFwdMessages().isEmpty()) {
            holder.conversationLastMsgText.setText("Пересланное сообщение");
            holder.conversationLastMsgText.setTextColor(holder.itemView.getContext().getColor(R.color.main_blue));
        } else if (items.get(position).getLast_message().getAction() != null) {
            holder.conversationLastMsgText.setTextColor(holder.itemView.getContext().getColor(R.color.main_blue));
            switch (items.get(position).getLast_message().getAction().getType()) {
                case CHAT_CREATE:
                    holder.conversationLastMsgText.setText("Создана беседа");
                    break;
                case CHAT_KICK_USER:
                    holder.conversationLastMsgText.setText("Пользователь покинул беседу");
                    break;
                case CHAT_INVITE_USER:
                    holder.conversationLastMsgText.setText("Приглашен пользователь");
                    break;
                case CHAT_PIN_MESSAGE:
                    holder.conversationLastMsgText.setText("Закреплено сообщение");
                    break;
                case CHAT_PHOTO_REMOVE:
                    holder.conversationLastMsgText.setText("Удалена фотография беседы");
                    break;
                case CHAT_PHOTO_UPDATE:
                    holder.conversationLastMsgText.setText("Обновлена фотография беседы");
                    break;
                case CHAT_TITLE_UPDATE:
                    holder.conversationLastMsgText.setText("Обновлено название беседы");
                    break;
                case CHAT_UNPIN_MESSAGE:
                    holder.conversationLastMsgText.setText("Откреплено сообщение");
                    break;
                case CHAT_INVITE_USER_BY_LINK:
                    holder.conversationLastMsgText.setText("Пользователь присоединился к беседе по ссылке");
                    break;
                default:
                    break;
            }
        } else {
            holder.conversationLastMsgText.setTextColor(holder.itemView.getContext().getColor(R.color.default_text));
        }
        if (items.get(position).getConversation().getUnread_count() > 0) {
            holder.conversationUnreadCount.setVisibility(View.VISIBLE);
            holder.conversationUnreadCount.setText(String.valueOf(items.get(position).getConversation().getUnread_count()));
        } else {
            holder.conversationUnreadCount.setVisibility(View.GONE);
        }
        if (items.get(position).getLast_message().getOut() == 1) {
            if (myIconUrl != null) {
                holder.conversationLastMsgIcon.setVisibility(View.VISIBLE);
                Glide.with(holder.itemView.getContext()).load(myIconUrl).into(holder.conversationLastMsgIcon);
            }
            if (items.get(position).getConversation().getOut_read() != items.get(position).getLast_message().getId()) {
                holder.outboxUnreadIndicator.setVisibility(View.VISIBLE);
            } else {
                holder.outboxUnreadIndicator.setVisibility(View.GONE);
            }
        } else if(items.get(position).getConversation().getPeer().getType().equals("chat")){
            holder.conversationLastMsgIcon.setVisibility(View.VISIBLE);
            holder.outboxUnreadIndicator.setVisibility(View.GONE);
            String chatUserIconUrl = getProfileInfo(items.get(position).getLast_message().getFrom_id()).getPhoto_50();
            Glide.with(holder.itemView.getContext()).load(chatUserIconUrl).into(holder.conversationLastMsgIcon);
        } else {
            holder.conversationLastMsgIcon.setVisibility(View.GONE);
//            holder.conversationLastMsgText.setPaddingRelative(23, 0, 0, 0);
            holder.outboxUnreadIndicator.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener((v) -> {
            String peerId = String.valueOf(items.get(position).getConversation().getPeer().getId());
            onDialogClickListener.onDialogClick(peerId);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private Profiles getProfileInfo(int id) {
        for (int i = 0; i < profiles.size(); i++) {
            if (id == profiles.get(i).getId()) {
                return profiles.get(i);
            }
        }
        return null;
    }

    private Groups getGroupInfo(int id) {
        for (int i = 0; i < groups.size(); i++) {
            if (Math.abs(id) == groups.get(i).getId()) {
                return groups.get(i);
            }
        }
        return null;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView conversationIcon;
        private TextView conversationTitle;
        private TextView conversationLastMsgDate;
        private CircleImageView conversationLastMsgIcon;
        private TextView conversationLastMsgText;
        private TextView conversationUnreadCount;
        private View outboxUnreadIndicator;
        private ImageView onlineIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            conversationIcon = itemView.findViewById(R.id.conversation_icon);
            conversationTitle = itemView.findViewById(R.id.conversation_title);
            conversationLastMsgDate = itemView.findViewById(R.id.conversation_last_msg_date);
            conversationLastMsgIcon = itemView.findViewById(R.id.conversation_last_msg_icon);
            conversationLastMsgText = itemView.findViewById(R.id.conversation_last_msg_text);
            conversationUnreadCount = itemView.findViewById(R.id.unread_count);
            outboxUnreadIndicator = itemView.findViewById(R.id.outbox_unreaded);
            onlineIcon = itemView.findViewById(R.id.online_icon);
        }
    }

    public void replaceItem(LastMessage item) {
        int peerId = item.getPeer_id();
        for (int i = 0; i < getItemCount(); i++) {
            if (items.get(i).getConversation().getPeer().getId() == peerId) {
                items.get(i).setLast_message(item);
            }
        }
        Collections.sort(items, (items, t1) -> (int) (t1.getLast_message().getDate() - items.getLast_message().getDate()));
        notifyDataSetChanged();
    }

    public void replaceItems(ConversationGet conversationGet) {
        List<Items> newItems = conversationGet.getResponse().getItems();
        for (int i = 0; i < newItems.size(); i++) {
            Items item = newItems.get(i);
            boolean isNew = true;
            for (int j = 0; j < getItemCount(); j++) {
                if (item.getConversation().getPeer().getId() == items.get(j).getConversation().getPeer().getId()) {
                    items.set(j, item);
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                items.add(item);
                if (conversationGet.getResponse().getProfiles() != null) {
                    profiles.addAll(conversationGet.getResponse().getProfiles());
                }
                if (conversationGet.getResponse().getGroups() != null) {
                    groups.addAll(conversationGet.getResponse().getGroups());
                }
            }
        }
        Collections.sort(items, (o1, o2) -> (int) (o2.getLast_message().getDate() - o1.getLast_message().getDate()));
        notifyDataSetChanged();
    }

    public void setOutboxReaded(int peerId) {
        for (int i = 0; i < getItemCount(); i++) {
            if (items.get(i).getConversation().getPeer().getId() == peerId) {
                items.get(i).getConversation().setOut_read(items.get(i).getLast_message().getId());

            }
        }
        notifyDataSetChanged();
    }

    public void setInboxReaded(int peerId) {
        for (int i = 0; i < getItemCount(); i++) {
            if (items.get(i).getConversation().getPeer().getId() == peerId) {
                items.get(i).getConversation().setUnread_count(0);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();
        if (layoutPosition == getItemCount() - 1 && holder.getLayoutPosition() < conversationsCount - 1) {

            loadMore.onLoadMore(items.get(getItemCount() - 1).getConversation().getLast_message_id());
        }
    }

    public void addMoreConversations(ConversationGet conversationGet) {
        List<Items> newItems = conversationGet.getResponse().getItems();
        List<Groups> newGroups = conversationGet.getResponse().getGroups();
        List<Profiles> newProfiles = conversationGet.getResponse().getProfiles();

        newItems.remove(0);
        items.addAll(newItems);
        groups.addAll(newGroups);
        profiles.addAll(newProfiles);

        notifyItemInserted(getItemCount());
    }

    public void setUserOnlinePhone(int userId){
        for(int i = 0; i < profiles.size(); i++){
            if(userId == profiles.get(i).getId()){
                profiles.get(i).setOnline_mobile(1);
            }
        }
        notifyDataSetChanged();
    }

    public void setUserOnlineWeb(int userId){
        for(int i = 0; i < profiles.size(); i++){
            if(userId == profiles.get(i).getId()){
                profiles.get(i).setOnline(1);
            }
        }
        notifyDataSetChanged();
    }

    public void setUserOffline(int userId){
        for(int i = 0; i < profiles.size(); i++){
            if(userId == profiles.get(i).getId()){
                profiles.get(i).setOnline_mobile(0);
                profiles.get(i).setOnline(0);
            }
        }
        notifyDataSetChanged();
    }
}

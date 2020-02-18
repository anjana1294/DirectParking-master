package com.directparking.app.ui.chat.chatlist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.chat.chatlist.model.ChatItem;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

/**
 * Created by root on 31/7/18.
 */

public class ChatListRecyclerAdapter extends RecyclerView.Adapter<ChatListRecyclerAdapter.ChatViewHolder> {
    Context mContext;
    List<ChatItem> chatItemList;
    Calendar calendar;

    public ChatListRecyclerAdapter(Context context, List<ChatItem> chatItems) {
        mContext = context;
        chatItemList = chatItems;
        this.calendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rideItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_chat_user, null);
        return new ChatViewHolder(rideItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        holder.userName.setText(chatItemList.get(position).getName());
        holder.lastMessage.setText(chatItemList.get(position).getMessage());
        if (!TextUtils.isEmpty(chatItemList.get(position).getTime())) {
            try {
                calendar.setTimeInMillis(Long.parseLong(chatItemList.get(position).getTime()));
                SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                String rideTime = df.format(calendar.getTime());
                if (!TextUtils.isEmpty(rideTime)) {
                    holder.messageTime.setText(rideTime);
                }
            } catch (IllegalArgumentException e) {
                Timber.e(e);
            }
        }

        try {
            Picasso.with(mContext).load(chatItemList.get(position).getProfile()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).fit().into(holder.userPic);
        } catch (Exception e) {
            e.printStackTrace();
            Picasso.with(mContext).load(R.mipmap.ic_launcher).fit().into(holder.userPic);
        }
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_user_profile)
        CircleImageView userPic;

        @BindView(R.id.tv_user_name)
        TextView userName;

        @BindView(R.id.tv_timestamp)
        TextView messageTime;

        @BindView(R.id.tv_last_message)
        TextView lastMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

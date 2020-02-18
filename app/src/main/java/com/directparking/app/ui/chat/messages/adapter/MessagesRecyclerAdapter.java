package com.directparking.app.ui.chat.messages.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.chat.messages.model.MessageItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 31/7/18.
 */

public class MessagesRecyclerAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<MessageItem> messageItems;
    Calendar calendar;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    String userId;

    public MessagesRecyclerAdapter(Context context, List<MessageItem> chatItems, String userId) {
        mContext = context;
        messageItems = chatItems;
        this.calendar = Calendar.getInstance();
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        MessageItem messageItem = messageItems.get(position);

        if (messageItem.getFromUserId().equals(this.userId))
            return VIEW_TYPE_MESSAGE_SENT;
        else
            return VIEW_TYPE_MESSAGE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageItem messageItem = messageItems.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bindMessage(messageItem);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bindMessage(messageItem);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }

    class SentMessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_message_profile)
        CircleImageView userPic;

        @BindView(R.id.tv_sent_message)
        TextView tvSendMessage;

        @BindView(R.id.tv_sent_message_time)
        TextView tvSentMessageTime;

        public SentMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindMessage(MessageItem item) {
            calendar.setTimeInMillis(Long.parseLong(item.getTime()));
            SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
            if (!TextUtils.isEmpty(item.getMessage()))
                tvSendMessage.setText(item.getMessage());
            tvSentMessageTime.setText(df.format(calendar.getTime()));
//            try {
//                Picasso.with(mContext).load(item.getProfile()).resize(80, 80).centerInside().placeholder(R.mipmap.ic_launcher)
//                        .error(R.mipmap.ic_launcher).into(userPic);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Picasso.with(mContext).load(R.mipmap.ic_launcher).resize(80, 80).centerInside().into(userPic);
//            }
        }
    }

    class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_message_profile)
        CircleImageView userPic;

        @BindView(R.id.tv_received_message)
        TextView tvReceivedMessage;

        @BindView(R.id.tv_received_message_Time)
        TextView tvReceivedMessageTime;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindMessage(MessageItem item) {
            if (!TextUtils.isEmpty(item.getMessage()))
                tvReceivedMessage.setText(item.getMessage());
            try {
                calendar.setTimeInMillis(Long.parseLong(item.getTime()));
                SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                tvReceivedMessageTime.setText(df.format(calendar.getTime()));
            }catch (Exception e){
                e.printStackTrace();
            }
//                Picasso.with(mContext).load(item.getProfile()).resize(80, 80).centerInside().placeholder(R.mipmap.ic_launcher)
//                        .error(R.mipmap.ic_launcher).into(userPic);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Picasso.with(mContext).load(R.mipmap.ic_launcher).resize(80, 80).centerInside().into(userPic);
//            }
        }
    }
}

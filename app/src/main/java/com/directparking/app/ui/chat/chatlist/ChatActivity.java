package com.directparking.app.ui.chat.chatlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.chat.chatlist.adapter.ChatListRecyclerAdapter;
import com.directparking.app.ui.chat.chatlist.model.ChatItem;
import com.directparking.app.ui.chat.messages.MessageActivity;
import com.directparking.app.ui.custom.EmptyRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class
ChatActivity extends BaseActivity implements ChatView {

    @Inject
    ChatPresenter presenter;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView rvChats;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private ChatListRecyclerAdapter chatListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        ((BaseApplication) getApplication()).getChatComponent().inject(this);
        presenter.setView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        rvChats.setLayoutManager(layoutManager);
        rvChats.addItemDecoration(new DividerItemDecoration(context(), DividerItemDecoration.VERTICAL));
        rvChats.setHasFixedSize(true);
        rvChats.setEmptyView(emptyView);
        presenter.fetchChatList();
        swipeLayout.setOnRefreshListener(() -> presenter.fetchChatList());
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvChats.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showChatList(List<ChatItem> chatItemList) {
        chatListRecyclerAdapter = new ChatListRecyclerAdapter(this, chatItemList);
        rvChats.setAdapter(chatListRecyclerAdapter);
        emptyView.setVisibility(View.GONE);
        rvChats.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClicked(ChatItem item) {
        Intent intentMessage = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("messageData", item);
        intentMessage.putExtras(bundle);
        startActivity(intentMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        ((BaseApplication) getApplication()).releaseChatComponent();
    }
}

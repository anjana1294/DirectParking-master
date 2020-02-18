package com.directparking.app.ui.chat.messages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.data.user.UserManager;
import com.directparking.app.fcm.BroadCastReceiver;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.chat.messages.adapter.MessagesRecyclerAdapter;
import com.directparking.app.ui.chat.messages.model.MessageItem;
import com.directparking.app.ui.custom.EmptyRecyclerView;
import com.directparking.app.ui.login.model.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MessageActivity extends BaseActivity implements MessageView {

    @Inject
    MessagePresenter presenter;

    @BindView(R.id.button_chatbox_send)
    Button btnSend;

    @BindView(R.id.edittext_chatbox)
    TextInputEditText etMessage;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView rvMessages;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    UserManager userManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    MessagesRecyclerAdapter messagesRecyclerAdapter;
    List<MessageItem> messageItems;

    private UserData userData;
    private String rideId;

    @OnClick(R.id.button_chatbox_send)
    void onSend(View v) {
        Calendar now = Calendar.getInstance();
        MessageItem messageItem = new MessageItem("", etMessage.getText().toString(), now.getTimeInMillis() + "", "", userManager.getUserId(), userData.getUserId(), rideId);

        if (this.messageItems != null) {
            this.messageItems.add(messageItem);
            messagesRecyclerAdapter.notifyDataSetChanged();
        } else {
            this.messageItems = new ArrayList<MessageItem>();
            this.messageItems.add(messageItem);
            messagesRecyclerAdapter = new MessagesRecyclerAdapter(this, this.messageItems, userManager.getUserId());
            rvMessages.setAdapter(messagesRecyclerAdapter);
            emptyView.setVisibility(View.GONE);
            rvMessages.setVisibility(View.VISIBLE);
        }
        scrollToBottom();
        etMessage.setText("");
        presenter.sendMessage(messageItem);
    }

    @OnTextChanged(value = R.id.edittext_chatbox,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterEditTextInput(Editable editable) {
        if (editable.length() > 0) {
            btnSend.setAlpha(1.0f);
            btnSend.setEnabled(true);
        } else {
            btnSend.setAlpha(.5f);
            btnSend.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        userData = (UserData) getIntent().getExtras().getSerializable("userData");
        rideId = getIntent().getExtras().getString("rideId");
        ButterKnife.bind(this);
        ((BaseApplication) getApplication()).getMessageComponent().inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setHasFixedSize(true);
        rvMessages.setEmptyView(emptyView);
        presenter.setView(this);

        toolbar.setTitle(userData.getFirstName() + " " + userData.getLastName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        presenter.fetchMessageList(rideId);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessageList(List<MessageItem> messageItems) {
        this.messageItems = messageItems;
        messagesRecyclerAdapter = new MessagesRecyclerAdapter(this, this.messageItems, userManager.getUserId());
        rvMessages.setAdapter(messagesRecyclerAdapter);
        emptyView.setVisibility(View.GONE);
        rvMessages.setVisibility(View.VISIBLE);
        scrollToBottom();
    }

    @Override
    public void showSuccessMessage(String msg) {

    }

    @Override
    public void updateMessageList(MessageItem messageRequest) {

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        presenter.destroy();
        ((BaseApplication) getApplication()).releaseMessageComponent();
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvMessages.setVisibility(View.GONE);
    }

    private void scrollToBottom() {
        messagesRecyclerAdapter.notifyDataSetChanged();
        if (messagesRecyclerAdapter.getItemCount() > 1)
            rvMessages.getLayoutManager().smoothScrollToPosition(rvMessages, null, messagesRecyclerAdapter.getItemCount() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver mMessageReceiver = new BroadCastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String chatData = intent.getExtras().getString("chatData");
            MessageItem messageItem = new Gson().fromJson(chatData, MessageItem.class);
            if (messageItems != null) {
                messageItems.add(messageItem);
            } else {
                messageItems = new ArrayList<MessageItem>();
                messageItems.add(messageItem);
                messagesRecyclerAdapter = new MessagesRecyclerAdapter(MessageActivity.this, messageItems, userManager.getUserId());
                rvMessages.setAdapter(messagesRecyclerAdapter);
                emptyView.setVisibility(View.GONE);
                rvMessages.setVisibility(View.VISIBLE);
            }
            scrollToBottom();
            messagesRecyclerAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onResume() {
        registerReceiver(mMessageReceiver, new IntentFilter("user_chat"));
        super.onResume();
    }
}

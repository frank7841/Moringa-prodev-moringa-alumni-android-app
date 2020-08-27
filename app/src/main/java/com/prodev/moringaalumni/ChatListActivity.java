package com.prodev.moringaalumni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chat_recyclerview) RecyclerView recyclerview;
    @BindView(R.id.proiflev) ImageView proifilev;
    @BindView(R.id.nameTv) TextView nameTv;
    @BindView(R.id.userStatusTv) TextView userStatusTv;
    @BindView(R.id.messageEt) EditText messageEt;
    @BindView(R.id.sendBtn) ImageButton sendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
         setSupportActionBar(toolbar);
         toolbar.setTitle("");
    }
}
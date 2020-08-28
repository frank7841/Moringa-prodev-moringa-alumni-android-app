package com.prodev.moringaalumni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.PendingIntent.getActivity;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerview;
    ImageView profileIv;
    TextView nameTv;
    TextView userStatusTv;
    EditText messageEt;
    ImageButton sendbtn;

    FirebaseAuth firebaseAuth;
    String hisUid;
    String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
         setSupportActionBar(toolbar);
         toolbar.setTitle("");
         toolbar.findViewById(R.id.toolbar);
         recyclerview.findViewById(R.id.chat_recyclerview);
         profileIv.findViewById(R.id.proifleIv);
         nameTv.findViewById(R.id.nameTv);
         userStatusTv.findViewById(R.id.userStatusTv);
         messageEt.findViewById(R.id.messageEt);
         sendbtn.findViewById(R.id.sendBtn);

         Intent intent = getIntent();
         hisUid = intent.getStringExtra("hisUid");

        //getting firebase auth
        firebaseAuth= FirebaseAuth.getInstance();
    }
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            myUid= user.getUid();


        }else {
            startActivity(new Intent(this,MainActivity.class));
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
//        menu.findItem(R.id.action_search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id ==R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}
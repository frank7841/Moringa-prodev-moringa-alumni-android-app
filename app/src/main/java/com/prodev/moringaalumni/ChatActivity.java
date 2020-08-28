package com.prodev.moringaalumni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

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


    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;
    List<ModelChat> chatList;
    AdapterChat adapterChat;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef;
    FirebaseAuth firebaseAuth;
    String hisUid;
    String myUid;
    String hisImage;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

         Intent intent = getIntent();
         hisUid = intent.getStringExtra("hisUid");

        //getting firebase auth
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersDbRef = firebaseDatabase.getReference("Users");
        //Searching Users to get their specific info
        Query userQuery = usersDbRef.orderByChild("uid").equalTo(hisUid);
        //getting the name and theg picture
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           //loop through the list to get exact data requested
           for (DataSnapshot ds: snapshot.getChildren()){
               //getting data
               String name = ""+ds.child("name").getValue();
               hisImage = ""+ds.child("image").getValue();
               //setting data in to the view
               nameTv.setText(name);
               try {
                   Picasso.get().load(hisUid).placeholder(R.drawable.ic_face).into(profileIv);

               } catch (Exception e) {
                   Picasso.get().load(R.drawable.ic_face).into(profileIv);
               }
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //send message on click
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting text from the editText widget
                String message = messageEt.getText().toString().trim();
                //checking if message is empty
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(ChatActivity.this, "Cannot send the empty message ...", Toast.LENGTH_SHORT).show();

                }else {
                    sendMessage(message);

                }
            }
        });
    }

    private void sendMessage(String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("receiver",hisUid);
        hashMap.put("message", message);
        databaseReference.child("Chats").push().setValue(hashMap);
        //reset EditText fild
        messageEt.setText("");
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
    protected void onStart() {
        checkUserStatus();
        super.onStart();
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
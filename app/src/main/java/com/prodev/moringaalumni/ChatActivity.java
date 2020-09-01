package com.prodev.moringaalumni;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
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
import com.prodev.moringaalumni.models.ModelChat;
import com.prodev.moringaalumni.models.ModelUser;
import com.prodev.moringaalumni.notifications.APIService;
import com.prodev.moringaalumni.notifications.Client;
import com.prodev.moringaalumni.notifications.Data;
import com.prodev.moringaalumni.notifications.Response;
import com.prodev.moringaalumni.notifications.Sender;
import com.prodev.moringaalumni.notifications.Token;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.PendingIntent.getActivity;

    public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerview;
    ImageView profileIv;
    TextView nameTv;
    TextView userStatusTv;
    EditText messageEt;
    ImageButton sendBtn;


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

        APIService apiService;
        boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        recyclerview = findViewById(R.id.chat_recyclerview);
        profileIv = findViewById(R.id.proifleIv);
        nameTv = findViewById(R.id.nameTv);
        userStatusTv = findViewById(R.id.userStatusTv);
        messageEt = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);


        apiService = Client.getRetrofit("https://fcm.googlepis.com").create(APIService.class);

        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisUid");
//        myUid = firebaseAuth.getCurrentUser().getUid();

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
                   Picasso.get().load(hisImage).placeholder(R.drawable.ic_face).into(profileIv);

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
        //send message on click
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify=true;

                String message=messageEt.getText().toString().trim();
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(ChatActivity.this, "Cannot send the empty message.....", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(message);

                }
                //reset editText after sending message;

                messageEt.setText("");
            }
        });
        readMessages();
        seenMessage();
    }

    private void seenMessage() {
        userRefForSeen=FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot ds: snapshot.getChildren()){
                ModelChat chat = ds.getValue(ModelChat.class);
                assert chat != null;
                if (Objects.equals(chat.getReceiver(), myUid) &&chat.getSender().equals(hisUid)){
                    HashMap<String, Object> hasSeenHashMap = new HashMap<>();
                    hasSeenHashMap.put("isSeen", true);
                    ds.getRef().updateChildren(hasSeenHashMap);
                }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessages() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    ModelChat chat = ds.getValue(ModelChat.class);
                    assert chat != null;
                    if (Objects.equals(chat.getReceiver(), myUid) && chat.getSender().equals(hisUid)||
                            Objects.equals(chat.getReceiver(), hisUid) && chat.getSender().equals(myUid)){
                        chatList.add(chat);
                    }
                    adapterChat = new AdapterChat(ChatActivity.this,chatList,hisImage);
                    adapterChat.notifyDataSetChanged();
                    recyclerview.setAdapter(adapterChat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("receiver",hisUid);
        hashMap.put("message", message);
        hashMap.put("timestamp",timestamp);
        hashMap.put("isSeen",false);
        databaseReference.child("Chats").push().setValue(hashMap);
        //reset EditText fild
        messageEt.setText("");

        String msg = message;
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelUser user = dataSnapshot.getValue(ModelUser.class);

                if(notify){
                    sendNotification(hisUid, user.getName(), message);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        private void sendNotification(final String hisUid, final String name, final String message) {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    Token token=ds.getValue(Token.class);
                    Data data=new Data(myUid, name + ":" + message, "New Message", hisUid, R.drawable.ic_default_img);

                    Sender sender=new Sender(data,token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    Toast.makeText(ChatActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {

                                }
                            });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
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
        private void checkOnlineStatus (String status){
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(myUid);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("onlineStatus", status);
            //update value of online status

            dbRef.updateChildren(hashMap);

        }

    @Override
    protected void onStart() {
        checkUserStatus();
        //set online
        checkOnlineStatus("online");
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        //set offline status with last seen time stamp
        userRefForSeen.removeEventListener(seenListener);

    }
    @Override
    protected  void onResume(){
        //set online
        checkOnlineStatus("online");
        super.onResume();
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
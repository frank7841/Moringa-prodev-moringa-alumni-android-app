package com.prodev.moringaalumni.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prodev.moringaalumni.models.ModelChat;
import com.prodev.moringaalumni.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {

    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;
    FirebaseUser fUser;

    public AdapterChat(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType ==MSG_TYPE_RIGHT){
        //inflate sender layout\
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new MyHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new MyHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String message = chatList.get(position).getMessage();
        String timestamp = chatList.get(position).getTimestamp();

        //converting timestamp to dd/mm/yy hh/mn am/pm
        Calendar cal =  Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timestamp));
        String dateTime = DateFormat.format("dd/mm/yyyy hh:mm am", cal).toString();

        holder.messageTv.setText(message);
        holder.timeTV.setText(dateTime);

        try{
            Picasso.get().load(imageUrl).into(holder.profiletV);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //click to show delete dialog
        holder.messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //show delete message confirm dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this message?");
                //delete button
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                //cancel delete button
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        deleteMessage(position);


                    }
                });
                //create and show dialog
                builder.create().show();

            }
        });
        //setting seen and delivered message status
        if (position==chatList.size()-1){
            if (chatList.get(position).isSeen()){
                holder.isSeenTv.setText("seen");
            }else {
                holder.isSeenTv.setText("delivered");
            }
        }else {
            holder.isSeenTv.setVisibility(View.GONE);
        }


    }

    private void deleteMessage(int position) {
        String myUID =FirebaseAuth.getInstance().getCurrentUser().getUid();
        /*Logic:
        Get timestamp of clicked message
        compare timestamp of clicked to all messages in chat
        Where both values matches delete that message
         */
        String msgTimeStamp = chatList.get(position).getTimestamp();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        Query query = dbRef.orderByChild("timestamp").equalTo(msgTimeStamp);
        query.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //if you want to allow snder to delete only his message when compared sender value to current user
                    if (ds.child("sender").getValue().equals(myUID)){
                           /*
                    We can do one of two things here
                    Remove message from chats
                    set value of message this message was deleted
                    so do whatever upu want
                   //Remove message from chats
                     */

                           //You can use either one of the two methods below ds and hashmap
                   // ds.getRef().removeValue();
                    // Set the value of message "This message was deleted..."
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("message", "This message was deleted...");
                    ds.getRef().updateChildren(hashMap);

                        Toast.makeText(context, "message deleted...", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(context, "You can delete only your messages", Toast.LENGTH_SHORT).show();
                    }





                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }


        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //getting curent signed in User
        fUser= FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
    //View holder class

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView profiletV;
        TextView messageTv, timeTV, isSeenTv ;
        LinearLayout messageLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //nitialising views
            profiletV= itemView.findViewById(R.id.proifleIv);
            messageTv= itemView.findViewById(R.id.messagegTv);
            timeTV= itemView.findViewById(R.id.timeTv);
            isSeenTv= itemView.findViewById(R.id.isSeenTv);
            messageTv= itemView.findViewById(R.id.messageLayout);

        }
    }
}

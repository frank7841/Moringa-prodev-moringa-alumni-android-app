package com.prodev.moringaalumni;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
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

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //nitialising views
            profiletV= itemView.findViewById(R.id.proifleIv);
            messageTv= itemView.findViewById(R.id.messagegTv);
            timeTV= itemView.findViewById(R.id.timeTv);
            isSeenTv= itemView.findViewById(R.id.isSeenTv);

        }
    }
}

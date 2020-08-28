package com.prodev.moringaalumni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {

    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;

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

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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

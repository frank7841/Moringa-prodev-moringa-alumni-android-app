package com.prodev.moringaalumni.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prodev.moringaalumni.R;

public class AdapterChatlist {
     class MyHolder extends RecyclerView.ViewHolder{
         ImageView profileIv,onlineStatusIv;
         TextView nameTv,lastMessageTv;

         public MyHolder(@NonNull View itemView) {
             super(itemView);
             profileIv = itemView.findViewById(R.id.proifleIv);
             onlineStatusIv = itemView.findViewById(R.id.onlineStatusIv);
             nameTv = itemView.findViewById(R.id.nameTv);
             lastMessageTv = itemView.findViewById(R.id.lastMessageTv);

         }
     }
}

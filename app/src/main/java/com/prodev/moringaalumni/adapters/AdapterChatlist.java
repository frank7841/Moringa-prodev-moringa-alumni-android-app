package com.prodev.moringaalumni.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prodev.moringaalumni.R;
import com.prodev.moringaalumni.models.ModelChatlist;
import com.prodev.moringaalumni.models.ModelUser;

import java.util.HashMap;
import java.util.List;

public class AdapterChatlist extends  RecyclerView.Adapter<AdapterChatlist.MyHolder> {
    Context context;
    List<ModelUser> userList;// getting user inforamtion
    private HashMap<String, String> lastMessageMap;

    public AdapterChatlist(Context context, List<ModelUser> userList, HashMap<String, String> lastMessageMap) {
        this.context = context;
        this.userList = userList;
        this.lastMessageMap = lastMessageMap;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the Layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_chatlist, parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

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

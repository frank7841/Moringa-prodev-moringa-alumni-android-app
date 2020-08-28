package com.prodev.moringaalumni;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

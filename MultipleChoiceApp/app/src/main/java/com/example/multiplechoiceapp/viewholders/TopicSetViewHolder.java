package com.example.multiplechoiceapp.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.R;


public class TopicSetViewHolder extends RecyclerView.ViewHolder{
    public TextView textName,textId,textTime;
    public ImageView imgTopic;
    private View view;
    public TopicSetViewHolder(@NonNull View itemView) {
        super(itemView);
        textName = (TextView) itemView.findViewById(R.id.textName);
        textId = (TextView) itemView.findViewById(R.id.textId);
        textTime = (TextView) itemView.findViewById(R.id.textTime);
        imgTopic = (ImageView) itemView.findViewById(R.id.imgTopic);
        view = itemView;
    }


}

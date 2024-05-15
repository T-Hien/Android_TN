package com.example.multiplechoiceapp.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder{

    public TextView txtContent,txtTime;
    public ImageView imgNotification;

    private View view;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.txtContent);
        txtTime = (TextView) itemView.findViewById(R.id.txtTime);
        imgNotification = (ImageView) itemView.findViewById(R.id.imgNotification);
        view = itemView;
    }
}

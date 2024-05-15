package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.viewholders.NotificationViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    List<Notification> list = Collections.emptyList();

    Context context;

    private int lastPosition = -1;

    public NotificationAdapter(List<Notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_card, parent, false);
        NotificationViewHolder notificationViewHolder = new NotificationViewHolder(view);
        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Resources resources = Resources.getSystem();
        holder.imgNotification.setImageResource(R.drawable.bell);
        holder.txtContent.setText(list.get(index).getContent());

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyy HH:mm");
        Date date = null;
        try {
            date = originalFormat.parse(list.get(index).getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String formattedDateTime = targetFormat.format(date);
        holder.txtTime.setText(formattedDateTime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

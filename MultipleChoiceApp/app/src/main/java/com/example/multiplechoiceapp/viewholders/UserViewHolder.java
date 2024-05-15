package com.example.multiplechoiceapp.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.utils.ClickListener;

import java.lang.ref.WeakReference;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView fullName;

    public ImageView imgUser;

    public Button btnSend, btnCancel;

    private View view;

    private WeakReference<ClickListener> listenerRef;


    public UserViewHolder(@NonNull View itemView,ClickListener listener) {
        super(itemView);
        listenerRef = new WeakReference<>(listener);
        fullName = (TextView) itemView.findViewById(R.id.txtFulName);
        imgUser = (ImageView) itemView.findViewById(R.id.imageUser);
        btnSend = (Button) itemView.findViewById(R.id.btnSend);
        btnCancel = (Button) itemView.findViewById(R.id.btnCancel);
        view = itemView;

        btnSend.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int status = -1;
        if (view.getId() == btnSend.getId()) {
            status = 0;
            btnSend.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        } else if (view.getId() == btnCancel.getId()) {
            status = 4;
            btnSend.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
        }

        listenerRef.get().onPositionClicked(getAdapterPosition(),status);
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}

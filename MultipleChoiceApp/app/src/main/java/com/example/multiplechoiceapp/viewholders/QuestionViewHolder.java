package com.example.multiplechoiceapp.viewholders;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.utils.ClickListener;

import java.lang.ref.WeakReference;

public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView textQuestionNumber;

    public ImageButton imageRemove;

    public Spinner spinner;

    public EditText textQuestionContent, selectionContentA, selectionContentB,
            selectionContentC, selectionContentD, answer;



    private View view;

    private WeakReference<ClickListener> listenerRef;

    public QuestionViewHolder(@NonNull View itemView,ClickListener listener) {
        super(itemView);
        listenerRef = new WeakReference<>(listener);
        imageRemove = (ImageButton) itemView.findViewById(R.id.imageRemove);
        textQuestionNumber = (TextView) itemView.findViewById(R.id.textQuestionNumber);
        textQuestionContent = (EditText) itemView.findViewById(R.id.textQuestionContent);
        selectionContentA = (EditText) itemView.findViewById(R.id.selectionContentA);
        selectionContentB = (EditText) itemView.findViewById(R.id.selectionContentB);
        selectionContentC = (EditText) itemView.findViewById(R.id.selectionContentC);
        selectionContentD = (EditText) itemView.findViewById(R.id.selectionContentD);
        spinner = itemView.findViewById(R.id.level);
        answer = (EditText) itemView.findViewById(R.id.answer);
        imageRemove.setOnClickListener(this);
        view = itemView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == imageRemove.getId()){
            listenerRef.get().onPositionClicked(getAdapterPosition(),0);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}

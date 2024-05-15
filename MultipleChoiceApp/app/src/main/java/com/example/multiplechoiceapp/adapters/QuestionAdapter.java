package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multiplechoiceapp.DTO.QuestionRequest;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.utils.ClickListener;
import com.example.multiplechoiceapp.viewholders.QuestionViewHolder;

import java.util.Collections;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    public List<QuestionRequest> list = Collections.emptyList();

    private TextView textQuestionNumber;

    private final ClickListener listener;


    private EditText textQuestionContent,
            selectionContentA, selectionContentB,selectionContentC, selectionContentD,answer;
    String[] levels = { "1 - dễ", "2 - vừa", "3 - khó", "4 - nâng cao"};

    Context context;

    public QuestionAdapter(ClickListener listener,List<QuestionRequest> list, Context context) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.create_question_layout,parent,false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(view,listener);
        return questionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

        final int index = holder.getAdapterPosition();
        holder.textQuestionNumber.setText("Câu: " +  (position + 1));
        holder.textQuestionContent.setText(list.get(position).getQuestionContent());
        holder.selectionContentA.setText(list.get(position).getSelection().get(0).getSelectionContent());
        holder.selectionContentB.setText(list.get(position).getSelection().get(1).getSelectionContent());
        holder.selectionContentC.setText(list.get(position).getSelection().get(2).getSelectionContent());
        holder.selectionContentD.setText(list.get(position).getSelection().get(3).getSelectionContent());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
        holder.answer.setText(list.get(position).getAnswer());
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedItem = parent.getItemAtPosition(pos).toString();
                list.get(position).setLevel(Integer.parseInt(String.valueOf(selectedItem.charAt(0))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý sự kiện khi không có mục nào được chọn
            }
        });

        holder.spinner.setSelection(list.get(position).getLevel() - 1);

        holder.textQuestionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(position).setQuestionContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.selectionContentA.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(position).getSelection().get(0).setSelectionContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.selectionContentB.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(position).getSelection().get(1).setSelectionContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.selectionContentC.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(position).getSelection().get(2).setSelectionContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.selectionContentD.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(position).getSelection().get(3).setSelectionContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(position).setAnswer(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.imageRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(list.get(position));
                notifyItemRemoved(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

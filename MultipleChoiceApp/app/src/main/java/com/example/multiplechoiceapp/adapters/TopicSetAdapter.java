package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.DTO.SharedTopicSetResponse;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.THien.ExamAgain;
import com.example.multiplechoiceapp.activities.THien.LevelQuestion;
import com.example.multiplechoiceapp.activities.THien.TopicSet;
import com.example.multiplechoiceapp.activities.TRIEU.ShareActivity;
import com.example.multiplechoiceapp.models.Topic_Set;
import com.example.multiplechoiceapp.viewholders.TopicSetViewHolder;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TopicSetAdapter extends RecyclerView.Adapter<TopicSetViewHolder> implements Filterable {
    List<SharedTopicSetResponse> topicSets;

    List<SharedTopicSetResponse> filterTopicSets;
    Context context;

    private int lastPosition = -1;

    public TopicSetAdapter(List<SharedTopicSetResponse> topicSet, Context context) {
        this.filterTopicSets = topicSet;
        topicSets = new ArrayList<>(topicSet);
        this.context = context;
    }
    @NonNull
    @Override
    public TopicSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.topic_card,parent,false);
        TopicSetViewHolder topicViewHolder = new TopicSetViewHolder(view);
        return topicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicSetViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Resources resources = Resources.getSystem();
        holder.imgTopic.setImageResource(R.drawable.sach2);
        holder.textName.setText("Người tạo: " + topicSets.get(index).getUsername());
        holder.textTime.setText("Thời gian: " + Math.round(topicSets.get(index).getDuration()) + " phút");
        holder.textId.setText(String.valueOf(topicSets.get(index).getName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                Float duration = topicSets.get(position).getDuration();
                String time = String.valueOf(duration);
                long topicSetCode = topicSets.get(position).getTopicSetId();
                Intent intent = new Intent(context, LevelQuestion.class);
                intent.putExtra("ID_TOPICSET", topicSetCode);
                intent.putExtra("USERNAME",username);
                intent.putExtra("DURATION",duration);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return topicSets.size();
    }

    private void setAnimation(View viewToAnimate, int position){
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SharedTopicSetResponse> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(topicSets);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SharedTopicSetResponse item : filterTopicSets) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            topicSets.clear();
            topicSets.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}

package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.models.Topic;

import java.util.List;

public class CustomAdapterTopic extends RecyclerView.Adapter<CustomAdapterTopic.ViewHolder> {
    private Context context;
    private List<Topic> data;
    private OnButtonClickListener onButtonClickListener;

    // Khai báo interface
    public interface OnButtonClickListener {
        void onButtonClick(Topic topic);
    }

    // Phương thức để thiết lập người nghe cho nút btnThi, btndetailTopicSet
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }

    public CustomAdapterTopic(Context context, int layout_list_topic_home, List<Topic> data) {
        this.context = context;
        this.data = data;
    }

    public Topic getItem(int position) {
        return data.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_topic_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = data.get(position);
        holder.txtTopicName.setText(topic.getTopicName());
        holder.btnTopicHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(topic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTopicName;
        Button btnTopicHome;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTopicName = itemView.findViewById(R.id.txtTopicHome);
            btnTopicHome = itemView.findViewById(R.id.btnTopicHome);
        }
    }
}

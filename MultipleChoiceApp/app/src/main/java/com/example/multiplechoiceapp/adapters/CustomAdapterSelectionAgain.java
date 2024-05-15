package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.models.Selection;

import java.util.List;

public class CustomAdapterSelectionAgain extends ArrayAdapter<Selection> {

    private Context context;
    private int resource;
    private List<Selection> data;
    private int selectedItem = -1;
    private  int selectedAnswer = -1;

    public CustomAdapterSelectionAgain(@NonNull Context context, int resource, List<Selection> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data= data;
    }
    public void setSelectedItem(int position, int dapan) {
        selectedItem = position;
        selectedAnswer = dapan;
        notifyDataSetChanged();
    }
    public void clearSelection() {
        selectedItem = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtAnswer = convertView.findViewById(R.id.txtAnswer);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Selection selection = data.get(position);
        viewHolder.txtAnswer.setText(selection.getSelectionContent());
        if (position == selectedAnswer) {
            if (selectedItem == selectedAnswer) {
                viewHolder.txtAnswer.setBackgroundColor(Color.parseColor("#29B82F"));
            } else if(selectedItem != -1){
                viewHolder.txtAnswer.setBackgroundColor(Color.parseColor("#29B82F"));
            }
            else{
                viewHolder.txtAnswer.setBackgroundColor(Color.parseColor("#B8090F"));            }
        } else if (position == selectedItem) {
            if (selectedItem == selectedAnswer) {
                viewHolder.txtAnswer.setBackgroundColor(Color.parseColor("#29B82F"));
            } else {
                viewHolder.txtAnswer.setBackgroundColor(Color.parseColor("#B8090F"));
            }
        } else {
            viewHolder.txtAnswer.setBackgroundColor(Color.TRANSPARENT); // Set default background color
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView txtAnswer;
    }

}

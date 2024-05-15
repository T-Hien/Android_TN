package com.example.multiplechoiceapp.activities.Hien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiplechoiceapp.R;

import java.util.List;

public class CustomAdapterRating extends ArrayAdapter<Object> {
    private Context context;
    private int resource;
    private List<Object> dataList;

    public CustomAdapterRating(@NonNull Context context, int resource, List<Object> dataList) {
        super(context, resource, dataList);
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        TextView tvSTT = convertView.findViewById(R.id.tvSTT);
        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvKetQua = convertView.findViewById(R.id.tvKetQua);
        TextView tvDiem = convertView.findViewById(R.id.tvDiem);

        Object data = dataList.get(position);
        if (data instanceof List) {
            List<Object> innerDataList = (List<Object>) data;
            tvSTT.setText(String.valueOf(position + 1));
            tvTen.setText(String.valueOf(innerDataList.get(0)));
            tvKetQua.setText(String.valueOf(innerDataList.get(1)) + "/" + String.valueOf(innerDataList.get(2)));
            tvDiem.setText(String.valueOf(innerDataList.get(3))); // Sử dụng String.valueOf() để chuyển đổi thành chuỗi
        }

        return convertView;
    }
}

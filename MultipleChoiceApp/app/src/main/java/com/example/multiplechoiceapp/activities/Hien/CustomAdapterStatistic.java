package com.example.multiplechoiceapp.activities.Hien;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiplechoiceapp.R;

import java.util.List;
import java.util.Random;

public class CustomAdapterStatistic extends ArrayAdapter {
    Context context;
    int resource;
    static List<Object> dataList;
    Random random = new Random();
    Button btnCT;

    public CustomAdapterStatistic(@NonNull Context context, int resource, List<Object> dataList) {
        super(context, resource, dataList);
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        ImageView imHinh2 = convertView.findViewById(R.id.imHinh2);
        TextView tvMaDe = convertView.findViewById(R.id.tvMaDe);
        TextView tvTenMon = convertView.findViewById(R.id.tvTenMon);
        TextView tvSoNguoiLam = convertView.findViewById(R.id.tvSoNguoiLam);
        btnCT = convertView.findViewById(R.id.btnXemChiTiet);

        Object data = dataList.get(position);
        if (data instanceof List) {
            List<Object> innerDataList = (List<Object>) data;
            tvMaDe.setText(String.valueOf(((Double) innerDataList.get(0)).intValue()));
            String defaultTenMon = "Môn: ";
            tvTenMon.setText(defaultTenMon + String.valueOf(innerDataList.get(1)));
            String defaultSoNguoiLam = "Số người làm bài: ";
            tvSoNguoiLam.setText(defaultSoNguoiLam + String.valueOf(innerDataList.get(2)));
            int randomNumber = random.nextInt(2);
            if (randomNumber == 0) {
                imHinh2.setImageResource(R.drawable.sach5);
            } else {
                imHinh2.setImageResource(R.drawable.sach7);
            }
        }

        btnCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Object> data = (List<Object>) dataList.get(position);
                String maDe = String.valueOf(data.get(0)); // Mã đề
                Intent intent = new Intent(context, RatingsActivity.class);
                intent.putExtra("MaDe", maDe); // Truyền mã đề
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public static List<Object> getDataList() {
        return dataList;
    }

    // Phương thức kiểm tra xem một chuỗi có phải là số hay không
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
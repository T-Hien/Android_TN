package com.example.multiplechoiceapp.activities.Hien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiplechoiceapp.R;

import java.util.List;
import java.util.Random;

public class CustomAdapterExam extends ArrayAdapter {
    Context context;
    int resource;

    static List<Object> dataList;
    Random random = new Random();
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(String maCD);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public CustomAdapterExam(@NonNull Context context, int resource, List<Object> dataList) {
        super(context, resource, dataList);
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        ImageView imHinh = convertView.findViewById(R.id.imHinh);
        TextView tvMaCD = convertView.findViewById(R.id.tvMaCD);
        TextView tvTenMon = convertView.findViewById(R.id.tvTenMon);
        TextView tvNguoiTao = convertView.findViewById(R.id.tvNguoiTao);
        ProgressBar tp1 = convertView.findViewById(R.id.tp1);
       /* Button btnXemKetQua = convertView.findViewById(R.id.btnXemKetQua);
        Button btnLamLai = convertView.findViewById(R.id.btnLamLai);*/

        Object data = dataList.get(position);
        if (data instanceof List) {
            List<Object> innerDataList = (List<Object>) data;
            tvMaCD.setText(String.valueOf(((Double) innerDataList.get(0)).intValue()));

            String defaultTenMon = "Môn: ";
            tvTenMon.setText(defaultTenMon + String.valueOf(innerDataList.get(1)));
            String defaultNguoiTao = "Người tạo: ";
            tvNguoiTao.setText(defaultNguoiTao + String.valueOf(innerDataList.get(2)));
            if (isNumeric(String.valueOf(innerDataList.get(3)))) {
                tp1.setProgress((int) (Double.parseDouble(String.valueOf(innerDataList.get(3))) * 10));
            } else {
                // Nếu không phải kiểu Double, xử lý theo trường hợp khác
                // Nếu không phải kiểu Double, chuyển đổi sang kiểu String trước khi chuyển đổi
                tp1.setProgress((int) (Double.parseDouble(String.valueOf(innerDataList.get(3))) * 10));
            }
            int randomNumber = random.nextInt(2);
            if (randomNumber == 0) {
                imHinh.setImageResource(R.drawable.sach5);
            } else {
                imHinh.setImageResource(R.drawable.sach7);
            }
        }
        /*
        btnXemKetQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(tvMaCD.getText().toString());
                }
            }
        });
        btnLamLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/



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

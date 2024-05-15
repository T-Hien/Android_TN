package com.example.multiplechoiceapp.activities.Hien;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.multiplechoiceapp.R;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PDFStatistical extends AppCompatActivity {

    private List<Object> dataListFromAdapter;

    Button btnTaoPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfstatistical);

        // Lấy danh sách dữ liệu từ CustomAdapterStatistic
        dataListFromAdapter = CustomAdapterStatistic.getDataList();

        btnTaoPDF = findViewById(R.id.btnTaoPDF); // Lấy tham chiếu đến button từ layout

        // Xử lý sự kiện khi nút được nhấn
        btnTaoPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức exportToPDF để tạo và xuất file PDF
                String filePath = getExternalFilesDir(null).getPath() + "/Statistical.pdf";
                exportToPDF(PDFStatistical.this, dataListFromAdapter, filePath);
            }
        });
    }

    // Lớp PDFExporter nằm trong cùng tệp với lớp PDFStatistical
    private static void exportToPDF(Context context, List<Object> dataList, String filePath) {
        // Kiểm tra xem có dữ liệu để xuất không
        if (dataList == null || dataList.isEmpty()) {
            Toast.makeText(context, "Không có dữ liệu để xuất ra PDF", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo tệp PDF
        Document document;
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            document = new Document(pdf);

            // Xuất dữ liệu từ danh sách ra file PDF
            for (Object data : dataList) {
                if (data instanceof List) {
                    List<Object> innerDataList = (List<Object>) data;
                    String line = "Ma Mon: " + String.valueOf(((Double) innerDataList.get(0)).intValue()) + "\n"
                            + "Ten Mon: " + String.valueOf(innerDataList.get(1)) + "\n"
                            + "So Nguoi Lam Bai: " + String.valueOf(innerDataList.get(2)) + "\n\n";
                    document.add(new Paragraph(line));
                }
            }

            // Đóng tệp PDF
            document.close();
// Di chuyển tệp PDF vào thư mục tải xuống
            moveFileToDownloads(context, filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Lỗi khi tạo PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức di chuyển tệp vào thư mục tải xuống
    private static void moveFileToDownloads(Context context, String filePath) {
        File currentFile = new File(filePath);
        File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File newFile = new File(downloadsDirectory, currentFile.getName());

        try {
            if (currentFile.renameTo(newFile)) {
                Toast.makeText(context, "Đã di chuyển tệp đến thư mục Tải xuống", Toast.LENGTH_SHORT).show();
                Log.d("MoveFile", "Tệp đã được di chuyển đến: " + newFile.getAbsolutePath());
            } else {
                Toast.makeText(context, "Không thể di chuyển tệp đến thư mục Tải xuống", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi khi di chuyển tệp: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
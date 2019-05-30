package com.pi.sample.testpdf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import id.pi.pdfviewpager.library.RemotePDFViewPager;
import id.pi.pdfviewpager.library.adapter.PDFPagerAdapter;
import id.pi.pdfviewpager.library.remote.DownloadFile;
import id.pi.pdfviewpager.library.util.FileUtil;


public class MainActivity extends AppCompatActivity implements DownloadFile.Listener {

    LinearLayout root;
    RemotePDFViewPager remotePDFViewPager;
    EditText etPdfUrl;
    Button btnDownload;
    PDFPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.remote_pdf_root);
        etPdfUrl = findViewById(R.id.et_pdfUrl);
        btnDownload = findViewById(R.id.btn_download);

        etPdfUrl.setText("http://www.pdf995.com/samples/pdf.pdf");
        setDownloadButtonListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    protected void setDownloadButtonListener() {
        final Context ctx = this;
        final DownloadFile.Listener listener = this;
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remotePDFViewPager = new RemotePDFViewPager(ctx, getUrlFromEditText(), listener);
                remotePDFViewPager.setId(R.id.pdfViewPager);
                hideDownloadButton();
            }
        });
    }

    protected String getUrlFromEditText() {
        return etPdfUrl.getText().toString().trim();
    }

    public static void open(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }

    public void showDownloadButton() {
        btnDownload.setVisibility(View.VISIBLE);
    }

    public void hideDownloadButton() {
        btnDownload.setVisibility(View.INVISIBLE);
    }

    public void updateLayout() {
        root.removeAllViewsInLayout();
//        root.addView(etPdfUrl,
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        root.addView(btnDownload,
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        root.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        System.out.println("URLNYA A " + url);
        System.out.println("URL DES A " + destinationPath);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
        showDownloadButton();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        showDownloadButton();
        System.out.println("PROGRESS FAILURE " + e.toString());
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        System.out.println("PROGRESS " + progress);
        System.out.println("PROGRESS T " + total);
    }
}


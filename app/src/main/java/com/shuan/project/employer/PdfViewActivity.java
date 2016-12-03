package com.shuan.Project.employer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shuan.Project.R;

public class PdfViewActivity extends AppCompatActivity {

    private WebView web;
    private Toolbar toolbar;
    private String path = "http://udyomitra.com/sample.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Candidate : "+getIntent().getStringExtra("name"));

        web = (WebView) findViewById(R.id.my_web);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        web.loadUrl("https://docs.google.com/gview?embedded=true&url=" + getIntent().getStringExtra("path"));

        //Toast.makeText(getApplicationContext(),getIntent().getStringExtra("path"),Toast.LENGTH_SHORT).show();

    }
}

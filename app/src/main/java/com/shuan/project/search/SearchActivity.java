package com.shuan.project.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.project.R;
import com.shuan.project.adapter.SerachAdapter;
import com.shuan.project.asyncTasks.Search;

import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back, close;
    private EditText search;
    private ListView list;
    private ProgressBar progressBar;
    private SerachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = (ImageButton) findViewById(R.id.back);
        close = (ImageButton) findViewById(R.id.close);
        search = (EditText) findViewById(R.id.search);
        list= (ListView) findViewById(R.id.search_list);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);

        back.setOnClickListener(this);
        close.setOnClickListener(this);

        new Search(SearchActivity.this,list,progressBar,search).execute();




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.close:
                search.setText("");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

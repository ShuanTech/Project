package com.shuan.Project.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shuan.Project.R;
import com.shuan.Project.Utils.Common;
import com.shuan.Project.adapter.SerachAdapter;
import com.shuan.Project.asyncTasks.Search;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back, close;
    private EditText search;
    private ListView list;
    private ProgressBar progressBar;
    private SerachAdapter adapter;
    private Common mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("1")) {
            setTheme(R.style.Junior);
        } else if (mApp.getPreference().getString(Common.LEVEL, "").equalsIgnoreCase("2")) {
            setTheme(R.style.Senior);
        } else {
            setTheme(R.style.AppBaseTheme);
        }

        super.onCreate(savedInstanceState);
        mApp = (Common) getApplicationContext();
        setContentView(R.layout.activity_search);

        back = (ImageButton) findViewById(R.id.back);
        close = (ImageButton) findViewById(R.id.close);
        search = (EditText) findViewById(R.id.search);
        list = (ListView) findViewById(R.id.search_list);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        back.setOnClickListener(this);
        close.setOnClickListener(this);

        new Search(SearchActivity.this, mApp.getPreference().getString(Common.u_id,""),list, progressBar, search).execute();



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

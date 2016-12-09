package com.shuan.Project.employee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shuan.Project.R;

public class EventViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        Toast.makeText(getApplicationContext(),"Under Processing",Toast.LENGTH_SHORT).show();


    }
}

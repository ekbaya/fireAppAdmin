package com.example.fireappadmin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fireappadmin.R;

public class DetailsActivity extends AppCompatActivity {
    private TextView timeTv, statusTv, nameTv, phoneTv;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        timeTv = findViewById(R.id.timeTv);
        statusTv = findViewById(R.id.statusTv);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);

        intent = getIntent();

        //set data
        timeTv.setText(intent.getStringExtra("time"));
        statusTv.setText(intent.getStringExtra("status"));
        nameTv.setText(intent.getStringExtra("name"));
        phoneTv.setText(intent.getStringExtra("phone"));
    }
}
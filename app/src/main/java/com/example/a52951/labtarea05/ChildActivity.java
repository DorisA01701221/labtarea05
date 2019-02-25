package com.example.a52951.labtarea05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {
    Intent currentIntent;
    String text;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        currentIntent = getIntent();
        text = currentIntent.getStringExtra("data");
        textView = findViewById(R.id.textView);
        textView.setText(text);
    }
}

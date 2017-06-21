package com.example.hiroki.dynamicvisualacuitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "abc", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(i);

    }
}

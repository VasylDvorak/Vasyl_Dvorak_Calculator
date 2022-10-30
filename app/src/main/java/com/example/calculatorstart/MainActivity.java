package com.example.calculatorstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView Txt;
    private float arg=3.14159265358f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Txt = findViewById(R.id.text);
        Txt.setText("Передаём число "+ String.valueOf(arg));
        findViewById(R.id.launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("vasyl://calculator"));
                intent.putExtra("first_argument", arg);
                startActivity(intent);

            }
        });
    }
}
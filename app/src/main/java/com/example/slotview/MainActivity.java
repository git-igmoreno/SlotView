package com.example.slotview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText txtSample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SlotView slotView= findViewById(R.id.slotView);
        slotView.setOffset(3);
        slotView.setDelay(2);
        button = findViewById(R.id.button);
        txtSample = findViewById(R.id.txtSample);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotView.slotTo(txtSample.getText().toString());
            }
        });





    }
}
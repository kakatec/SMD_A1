package com.example.smd_a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button startQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        startQuiz = findViewById(R.id.startQuiz);

        startQuiz.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("user_name", name);
            startActivity(intent);
        });
    }
}
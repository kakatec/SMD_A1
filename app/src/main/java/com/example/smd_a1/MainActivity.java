package com.example.smd_a1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button startQuiz;
    private static final String TAG = "MainActivity"; // For debugging logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        startQuiz = findViewById(R.id.startQuiz);

        // Initially disable button
        startQuiz.setEnabled(true);
        startQuiz.setAlpha(0.5f); // Make it appear disabled

        // Listen for text changes in the EditText
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isNameEntered = s.toString().trim().length() > 0;
                startQuiz.setEnabled(isNameEntered);
                startQuiz.setAlpha(isNameEntered ? 1.0f : 0.5f);

                // Reset background color if user starts typing
                if (isNameEntered) {
                    nameInput.setBackgroundColor(Color.WHITE); // Reset background
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Start quiz when button is clicked
        startQuiz.setOnClickListener(v -> {


            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                // Show error message

                Toast.makeText(MainActivity.this, "Name field is required", Toast.LENGTH_SHORT).show();
            } else {

                // Proceed to next activity if name is entered
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("user_name", name);
                startActivity(intent);
            }
        });
    }
}

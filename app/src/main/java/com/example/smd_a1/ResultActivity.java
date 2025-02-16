package com.example.smd_a1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView resultText,nameText;
    private Button  shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.resultText);

        shareButton = findViewById(R.id.shareButton);  // New Share Button
        nameText=findViewById(R.id.nameText);
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 10);
        String userName = getIntent().getStringExtra("user_name");

        resultText.setText( score + " / " + totalQuestions);
        nameText.setText(userName);


        // Share Score using Implicit Intent
        shareButton.setOnClickListener(v -> {
            String shareMessage = "I scored " + score + " out of " + totalQuestions + " in the Quiz App! 🎉 Try to beat my score!";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share your score via"));
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Close the activity when back arrow is pressed
        return true;
    }
}

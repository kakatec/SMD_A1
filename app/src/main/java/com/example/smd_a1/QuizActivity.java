package com.example.smd_a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, username;
    private RadioGroup optionsGroup;
    private Button nextButton;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private String[] questions = {
            "What is the capital of France?",
            "Which programming language is used for Android development?"
    };

    private String[][] options = {
            {"Paris", "London", "Rome", "Berlin"},
            {"Java", "Python", "C#", "Swift"}
    };

    private String[] correctAnswers = {"Paris", "Java"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        username = findViewById(R.id.username);
        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextButton = findViewById(R.id.nextButton);

        String name = getIntent().getStringExtra("user_name");
        username.setText("Hello, " + name);

        loadQuestion();

        nextButton.setOnClickListener(v -> {
            checkAnswer();
            currentQuestionIndex++;

            if (currentQuestionIndex < questions.length) {
                loadQuestion();
            } else {
//                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
//                intent.putExtra("score", score);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        optionsGroup.removeAllViews();

        for (String option : options[currentQuestionIndex]) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsGroup.addView(radioButton);
        }
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedOption = findViewById(selectedId);
            if (selectedOption.getText().toString().equals(correctAnswers[currentQuestionIndex])) {
                score++;
            }
        }
    }
}
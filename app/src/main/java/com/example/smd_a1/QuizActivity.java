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

    private TextView questionText, username, questionNumber;
    private RadioGroup optionsGroup;
    private Button nextButton, prevButton;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private String[] questions = {
            "What is the capital of France?",
            "Which programming language is used for Android development?",
            "What is 2 + 2?",
            "Who developed the theory of relativity?",
            "Which is the largest planet in our solar system?",
            "What is the square root of 64?",
            "Which ocean is the largest?",
            "Who painted the Mona Lisa?",
            "What is the chemical symbol for gold?",
            "What year did World War II end?"
    };

    private String[][] options = {
            {"Paris", "London", "Rome", "Berlin"},
            {"Java", "Python", "C#", "Swift"},
            {"3", "4", "5", "6"},
            {"Newton", "Einstein", "Galileo", "Tesla"},
            {"Earth", "Mars", "Jupiter", "Saturn"},
            {"6", "7", "8", "9"},
            {"Atlantic", "Indian", "Pacific", "Arctic"},
            {"Van Gogh", "Da Vinci", "Picasso", "Rembrandt"},
            {"Au", "Ag", "Fe", "Hg"},
            {"1942", "1945", "1950", "1939"}
    };

    private String[] correctAnswers = {
            "Paris", "Java", "4", "Einstein", "Jupiter",
            "8", "Pacific", "Da Vinci", "Au", "1945"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        username = findViewById(R.id.username);
        questionText = findViewById(R.id.questionText);
        questionNumber = findViewById(R.id.questionNumber);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);

        String name = getIntent().getStringExtra("user_name");
        username.setText("Hello, " + name);

        loadQuestion();

        prevButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });

        nextButton.setOnClickListener(v -> {
            checkAnswer();
            currentQuestionIndex++;

            if (currentQuestionIndex < questions.length) {
                loadQuestion();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("totalQuestions", questions.length);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        questionNumber.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.length);
        optionsGroup.removeAllViews();

        for (String option : options[currentQuestionIndex]) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsGroup.addView(radioButton);
        }

        prevButton.setEnabled(currentQuestionIndex != 0);
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

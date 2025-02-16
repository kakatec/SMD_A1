package com.example.smd_a1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, questionNumber, prevButton;
    private RadioGroup optionsGroup;
    private Button nextButton;
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

    // Store selected answers for each question
    private Map<Integer, String> selectedAnswers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        prevButton = findViewById(R.id.prevButton);
        questionText = findViewById(R.id.questionText);
        questionNumber = findViewById(R.id.questionNumber);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setEnabled(false); // Disable Next button initially

        loadQuestion();

        prevButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (optionsGroup.getCheckedRadioButtonId() == -1) {
                // No option selected, show a warning
                Toast.makeText(QuizActivity.this, "Please select an option!", Toast.LENGTH_SHORT).show();
            } else {
                checkAnswer();
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.length) {
                    loadQuestion();
                } else {
                    // Move to result activity
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("totalQuestions", questions.length);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private void loadQuestion() {
        prevButton.setText("< Previous");
        prevButton.setTextColor(Color.BLACK);
        prevButton.setTypeface(null, Typeface.BOLD);
        prevButton.setGravity(Gravity.START);

        questionNumber.setText((currentQuestionIndex + 1) + "/" + questions.length);
        questionText.setText(questions[currentQuestionIndex]);
        optionsGroup.removeAllViews();

        nextButton.setEnabled(false); // Disable Next button initially

        for (int i = 0; i < options[currentQuestionIndex].length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options[currentQuestionIndex][i]);
            radioButton.setTextSize(16);
            radioButton.setTextColor(Color.BLACK);
            radioButton.setPadding(20, 20, 20, 20);
            radioButton.setBackgroundResource(R.drawable.radio_button_default);
            radioButton.setButtonDrawable(null);
            radioButton.setId(View.generateViewId()); // Assign unique ID

            radioButton.setOnClickListener(v -> updateSelection(radioButton));

            optionsGroup.addView(radioButton);

            // Restore previously selected answer
            if (selectedAnswers.containsKey(currentQuestionIndex) &&
                    selectedAnswers.get(currentQuestionIndex).equals(options[currentQuestionIndex][i])) {
                radioButton.setChecked(true);
                nextButton.setEnabled(true); // Enable Next if previously selected
            }
        }

        // Enable Next button when an option is selected
        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            nextButton.setEnabled(true);
        });

        prevButton.setEnabled(currentQuestionIndex != 0);
    }


    private void updateSelection(RadioButton selectedRadioButton) {
        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) optionsGroup.getChildAt(i);
            if (radioButton == selectedRadioButton) {
                radioButton.setBackgroundResource(R.drawable.radio_button_selected);
            } else {
                radioButton.setBackgroundResource(R.drawable.radio_button_default);
            }
        }

        // Store the selected answer
        selectedAnswers.put(currentQuestionIndex, selectedRadioButton.getText().toString());
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedOption = findViewById(selectedId);
            String selectedText = selectedOption.getText().toString();

            // Store the answer
            selectedAnswers.put(currentQuestionIndex, selectedText);

            // Check if it's correct
            if (selectedText.equals(correctAnswers[currentQuestionIndex])) {
                score++;
            }
        }
    }
}

package no.hvl.dat153.quizapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import no.hvl.dat153.quizapp.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_AMOUNT = "EXTRA_QUESTION_AMOUNT";

    private ActivityQuizBinding binding;
    private static final String TAG = "QuizActivity";
    private Integer questionAmount = 1;
    private Integer currentQuestionIndex = 0;
    private Integer correctAnswers = 0;
    private List<QuizQuestion> questions;

    private void loadNextQuestion() {
        if (currentQuestionIndex < questionAmount) {
            loadQuestionFragment(questions.get(currentQuestionIndex));
        } else {
            // Load the results fragment
            Toast.makeText(this, "Du er ferdig", Toast.LENGTH_SHORT).show();
            finish();
        }
        currentQuestionIndex++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        questionAmount = getIntent().getIntExtra(EXTRA_QUESTION_AMOUNT, 1);

        Log.d(TAG, "onCreate: load " + questionAmount + " questions");

        questions = List.of(
                new QuizQuestion(R.drawable.feet, "Feet", "Hat", "Toes", "test", "toots", "bird", "cristoffmisbruker"),
                new QuizQuestion(R.drawable.bird, "fågel", "Feet", "fugl", "Bird", "BIRD", "bird", "B|RD", "B1RD"),
                new QuizQuestion(R.drawable.cok, "Cok", "Coke", "Coca coke", "Cola", "Pebis", "Bepis", "coca cola"),
                new QuizQuestion(R.drawable.giiislefoss, "giiislefoss", "mann", "gisle", "foss", "gislefoss", "gissel", "kristian")
        );

        binding.pageTitle.setOnClickListener(v -> {
            loadNextQuestion();
        });

        // Dynamically load the fragment
        if (savedInstanceState == null) {
            loadNextQuestion();
        }
    }

    private void loadQuestionFragment(QuizQuestion q) {
        QuestionFragment questionFragment = QuestionFragment.newInstance(q.getQuestionImage(), q.getAnswers());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.quizFragmentContainer.getId(), questionFragment);
        fragmentTransaction.commit();
    }
}
package no.hvl.dat153.quizapp.quiz;

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

import java.util.Locale;

import no.hvl.dat153.quizapp.help.QuizQuestion;
import no.hvl.dat153.quizapp.databinding.ActivityQuizBinding;
import no.hvl.dat153.quizapp.help.Gallery;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_AMOUNT = "EXTRA_QUESTION_AMOUNT";

    private ActivityQuizBinding binding;
    private static final String TAG = "QuizActivity";
    private Integer questionAmount = 1;
    private Integer currentQuestionIndex = 0;
    private Integer correctAnswers = 0;

    private QuizQuestion currentQuestion;

    private void loadNextQuestion() {
        if (currentQuestionIndex < questionAmount) {
            currentQuestion = Gallery.getInstance().getQuestions().get(currentQuestionIndex);
            loadQuestionFragment(currentQuestion);
        } else {
            // Load the results fragment
            Toast.makeText(this, String.format(Locale.ENGLISH, "Du fikk %d/%d riktige svar!", correctAnswers, questionAmount), Toast.LENGTH_SHORT).show();
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


        binding.pageTitle.setOnClickListener(v -> {
            loadNextQuestion();
        });

        // Dynamically load the fragment
        if (savedInstanceState == null) {
            loadNextQuestion();
        }

        getSupportFragmentManager().setFragmentResultListener(QuestionFragment.FRAGMENT_RESULT, this, (requestKey, bundle) -> {
            String answer = bundle.getString(QuestionFragment.ARG_RESULT);
            Log.d(TAG, "Button clicked with answer: " + answer);
            if (currentQuestion.getCorrectAnswer().equals(answer)) {
                correctAnswers++;
                Toast.makeText(this, "Riktig svar!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Feil svar!", Toast.LENGTH_SHORT).show();
            }
            loadNextQuestion();
        });
    }

    private void loadQuestionFragment(QuizQuestion q) {
        if (q == null) {
            Log.e(TAG, "loadQuestionFragment: Question is null");
            return;
        }
        QuestionFragment questionFragment = QuestionFragment.newInstance(q.getImageUri(), q.getAnswers());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.quizFragmentContainer.getId(), questionFragment);
        fragmentTransaction.commit();
    }
}
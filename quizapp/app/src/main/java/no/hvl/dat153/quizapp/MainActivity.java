package no.hvl.dat153.quizapp;

import static no.hvl.dat153.quizapp.quiz.QuizActivity.EXTRA_QUESTION_AMOUNT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import no.hvl.dat153.quizapp.databinding.ActivityMainBinding;
import no.hvl.dat153.quizapp.gallery.GalleryActivity;
import no.hvl.dat153.quizapp.help.Gallery;
import no.hvl.dat153.quizapp.quiz.QuizActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Integer questionAmount = 1;

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.quizQuestionsAmountSelector.setMax(Gallery.getInstance().getQuestions().size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        var listener = getListener(this);

        binding.quizQuestionsAmountSelector.setOnSeekBarChangeListener(listener);

        binding.buttonStartQuiz.setOnClickListener(v -> {
            Toast.makeText(this, "Quiz started!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra(EXTRA_QUESTION_AMOUNT, questionAmount);
            startActivity(intent);

        });

        binding.buttonGallery.setOnClickListener(v -> {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
        });

    }

    private SeekBar.OnSeekBarChangeListener getListener(MainActivity mainActivity) {
        return new SeekBar.OnSeekBarChangeListener() {
            private static final String TAG = "ProgressBar";
            private final MainActivity parentActivity = mainActivity;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                parentActivity.setQuestionAmount(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: " + parentActivity.getQuestionAmount());
                Toast.makeText(parentActivity, "Du har valgt " + parentActivity.getQuestionAmount() + " spørsmål", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
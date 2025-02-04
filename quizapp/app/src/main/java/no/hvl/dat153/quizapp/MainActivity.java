package no.hvl.dat153.quizapp;

import static no.hvl.dat153.quizapp.QuizActivity.EXTRA_QUESTION_AMOUNT;

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

import java.util.List;

import no.hvl.dat153.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private Integer questionAmount = 1;

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gallery.getInstance().addQuestions(
                new QuizQuestion(R.drawable.feet, "Feet", "Hat", "Toes", "test", "toots", "bird", "cristoffmisbruker", "føtter", "ting", "som", "kan", "funke", "superlang", "tiss", "ligma", "balls"),
                new QuizQuestion(R.drawable.bird, "fågel", "Feet", "fugl", "Bird", "BIRD", "bird", "B|RD", "B1RD"),
                new QuizQuestion(R.drawable.cok, "Cok", "Coke", "Coca coke", "Cola", "Pebis", "Bepis", "coca cola"),
                new QuizQuestion(R.drawable.giiislefoss, "giiislefoss", "mann", "gisle", "foss", "gislefoss", "gissel", "kristian")
        );


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

        binding.quizQuestionsAmountSelector.setMax(Gallery.getInstance().getQuestions().size());

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
            private final MainActivity parentActivity = mainActivity;
            private static final String TAG = "ProgressBar";


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
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

import no.hvl.dat153.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

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

        var poopies = this;

        var listener = new SeekBar.OnSeekBarChangeListener() {
            private static final String TAG = "ProgressBar";
            int amount = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: " + amount);
                Toast.makeText(poopies, "Du har valgt " + amount + " spørsmål", Toast.LENGTH_SHORT).show();
            }
        };

        binding.quizQuestionsAmountSelector.setOnSeekBarChangeListener(listener);

        binding.buttonStartQuiz.setOnClickListener(v -> {
            Toast.makeText(this, "Quiz started!", Toast.LENGTH_SHORT).show();

            // start the quiz activity
            Intent intent = new Intent(this, QuizActivity.class);

            intent.putExtra(EXTRA_QUESTION_AMOUNT, listener.amount);
            startActivity(intent);

        });
    }
}
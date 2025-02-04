package no.hvl.dat153.quizapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import no.hvl.dat153.quizapp.databinding.ActivityGalleryBinding;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    private ActivityGalleryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        EdgeToEdge.enable(this);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rv = binding.galleryItemContainer;
        rv.setLayoutManager(new LinearLayoutManager(this));
        QuizQuestionAdapter adapter = new QuizQuestionAdapter(Gallery.getInstance().getQuestions());
        rv.setAdapter(adapter);
    }

    private void createGalleryElement(QuizQuestion question) {
        var noe = R.layout.gallery_item;
    }
}
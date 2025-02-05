package no.hvl.dat153.quizapp.gallery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import no.hvl.dat153.quizapp.help.Gallery;
import no.hvl.dat153.quizapp.help.QuizQuestionAdapter;
import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.databinding.ActivityGalleryBinding;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    private ActivityGalleryBinding binding;

    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 1002;
    private static final String[] REQUIRED_PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };

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

        if (allPermissionsGranted()) {
            // Permissions are already granted, proceed with your logic
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        RecyclerView rv = binding.galleryItemContainer;
        rv.setLayoutManager(new LinearLayoutManager(this));
        QuizQuestionAdapter adapter = new QuizQuestionAdapter(Gallery.getInstance().getQuestions());
        rv.setAdapter(adapter);



        binding.addGalleryImage.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add Image")
                    .setMessage("Add image from phone gallery or take a new picture?")
                    .setPositiveButton(R.string.image_dialog_select_gallery, (dialogInterface, which) -> {

                    })
                    .setNeutralButton(R.string.image_dialog_camera, (dialogInterface, which) -> {

                    })
                    .setNegativeButton(R.string.image_dialog_cancel, (dialogInterface, which) -> {

                    })
                    .create();
            dialog.show();
        });
    }

    private void openDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_OPEN_DOCUMENT);
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                // Permissions granted, proceed with your logic
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void showCreateQuizQuestionDialog(Uri imageUri) {
        // open the newQuestionTextFragment dialog
        NewQuestionTextFragment dialog = NewQuestionTextFragment.newInstance(imageUri);
        dialog.show(getSupportFragmentManager(), "NewQuestionTextFragment");
    }


}
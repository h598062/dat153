package no.hvl.dat153.quizapp.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.databinding.ActivityGalleryBinding;
import no.hvl.dat153.quizapp.help.Gallery;
import no.hvl.dat153.quizapp.help.QuizQuestionAdapter;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 1002;
    private static final int REQUEST_CODE_TAKE_PICTURE = 1003;
    private ActivityGalleryBinding binding;
    private Uri photoUri;

    private final int gallerySize = Gallery.getInstance().getQuestions().size();

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
        QuizQuestionAdapter adapter = new QuizQuestionAdapter();
        rv.setAdapter(adapter);


        binding.addGalleryImage.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add Image")
                    .setMessage("Add image from phone gallery or take a new picture?")
                    .setPositiveButton(R.string.image_dialog_select_gallery, (dialogInterface, which) -> {
                        openDocument();
                    })
                    .setNeutralButton(R.string.image_dialog_camera, (dialogInterface, which) -> {
                        takePicture();
                    })
                    .setNegativeButton(R.string.image_dialog_cancel, (dialogInterface, which) -> {
                        dialogInterface.dismiss();
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

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, "no.hvl.dat153.quizapp.provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            }
        }
    }

    private File createImageFile() {
        String timeStamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_DOCUMENT && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();

                // Get permanent read permission
                getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                // Handle the selected image URI
                showCreateQuizQuestionDialog(imageUri);
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
            if (data != null) {
                // Handle the selected image URI
                showCreateQuizQuestionDialog(photoUri);
            }
        }
    }

    public void showCreateQuizQuestionDialog(Uri imageUri) {
        // open the newQuestionTextFragment dialog
        NewQuestionTextFragment dialog = NewQuestionTextFragment.newInstance(imageUri);
        dialog.show(getSupportFragmentManager(), "NewQuestionTextFragment");
    }

    public void updateRecyclerView() {
        binding.galleryItemContainer.getAdapter().notifyDataSetChanged();
    }


}
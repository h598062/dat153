package no.hvl.dat153.quizapp.gallery;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import no.hvl.dat153.quizapp.databinding.AddQuestionTextAnswersFragmentBinding;
import no.hvl.dat153.quizapp.help.Gallery;
import no.hvl.dat153.quizapp.help.QuizQuestion;

public class NewQuestionTextFragment extends DialogFragment {

    private static final String TAG = "NewQuestionTextFragment";
    public static final String IMAGE_URI = "image_uri";

    private AddQuestionTextAnswersFragmentBinding binding;


    public static NewQuestionTextFragment newInstance(Uri imageUri) {
        NewQuestionTextFragment fragment = new NewQuestionTextFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URI, imageUri.toString());
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddQuestionTextAnswersFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Uri imageUri = Uri.parse(this.getArguments().getString(IMAGE_URI));

        binding.newImagePreview.setImageURI(imageUri);


        binding.newQuizItemSubmit.setOnClickListener(v -> {
            String correct = binding.newQCorrect.getEditText().getText().toString();
            String incorrect1 = binding.newQIncorrect1.getEditText().getText().toString();
            String incorrect2 = binding.newQIncorrect2.getEditText().getText().toString();
            QuizQuestion q = new QuizQuestion(imageUri, correct, incorrect1, incorrect2);
            Gallery.getInstance().addQuestion(q);
            dismiss();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() instanceof GalleryActivity) {
            ((GalleryActivity) getActivity()).updateRecyclerView();
        }
    }
}

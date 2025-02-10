package no.hvl.dat153.quizapp.quiz;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import no.hvl.dat153.quizapp.databinding.FragmentQuestionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    public static final String ARG_RESULT = "answer";
    public static final String FRAGMENT_RESULT = "answer";
    private static final String TAG = "QuestionFragment";
    private static final String ARG_QUIZIMAGE = "QuizImageResource";
    private static final String ARG_QUIZANSWER = "QuizAnswer";
    private Uri quizImageUri;
    private String[] quizAnswer;

    private FragmentQuestionBinding binding;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quizImageUri Resource int for the quiz image. Use R.drawable.*.
     * @param quizAnswer   The correct and incorrect answers for the quiz.
     * @return A new instance of fragment QuestionFragment.
     */
    public static QuestionFragment newInstance(Uri quizImageUri, String[] quizAnswer) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUIZIMAGE, quizImageUri.toString());
        args.putStringArray(ARG_QUIZANSWER, quizAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizImageUri = Uri.parse(getArguments().getString(ARG_QUIZIMAGE));
            quizAnswer = getArguments().getStringArray(ARG_QUIZANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.quizImage.setImageURI(quizImageUri);

        binding.buttonAnsA.setText(quizAnswer[0]);
        binding.buttonAnsB.setText(quizAnswer[1]);
        binding.buttonAnsC.setText(quizAnswer[2]);

        binding.buttonAnsA.setOnClickListener(v -> clickedButton(quizAnswer[0]));
        binding.buttonAnsB.setOnClickListener(v -> clickedButton(quizAnswer[1]));
        binding.buttonAnsC.setOnClickListener(v -> clickedButton(quizAnswer[2]));


        return view;
    }

    private void clickedButton(String answer) {
        Bundle result = new Bundle();
        result.putString(ARG_RESULT, answer);
        getParentFragmentManager().setFragmentResult(FRAGMENT_RESULT, result);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
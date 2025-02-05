package no.hvl.dat153.quizapp.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.hvl.dat153.quizapp.databinding.FragmentQuestionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";
    private static final String ARG_QUIZIMAGE = "QuizImageResource";
    private static final String ARG_QUIZANSWER = "QuizAnswer";

    private Integer quizImageResource;
    private String[] quizAnswer;

    private FragmentQuestionBinding binding;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quizImageResource Resource int for the quiz image. Use R.drawable.*.
     * @param quizAnswer        The correct and incorrect answers for the quiz.
     * @return A new instance of fragment QuestionFragment.
     */
    public static QuestionFragment newInstance(Integer quizImageResource, String[] quizAnswer) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUIZIMAGE, quizImageResource);
        args.putStringArray(ARG_QUIZANSWER, quizAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizImageResource = getArguments().getInt(ARG_QUIZIMAGE);
            quizAnswer = getArguments().getStringArray(ARG_QUIZANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.quizImage.setImageResource(quizImageResource);

        binding.buttonAnsA.setText(quizAnswer[0]);
        binding.buttonAnsB.setText(quizAnswer[1]);
        binding.buttonAnsC.setText(quizAnswer[2]);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package no.hvl.dat153.quizapp.help;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.hvl.dat153.quizapp.R;

public class QuizQuestionAdapter extends RecyclerView.Adapter<QuizQuestionAdapter.ViewHolder> {
    private final List<QuizQuestion> quizQuestions;

    private static final String TAG = "QuizQuestionAdapter";

    public QuizQuestionAdapter(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizQuestion question = quizQuestions.get(position);
        if (question.getQuestionImage() != null) {
            holder.imageView.setImageResource(question.getQuestionImage());
        } else if (question.getImageUri() != null) {
            holder.imageView.setImageURI(Uri.parse(question.getImageUri()));
        } else {
            holder.imageView.setImageDrawable(AppCompatResources.getDrawable(holder.imageView.getContext(), R.drawable.ic_launcher_background));
            holder.imageView.setColorFilter(Color.GRAY);
            Log.d(TAG, "onBindViewHolder: Quiz question has no image...");
        }
        holder.correctAnswer.setText(question.getCorrectAnswer());
        holder.incorrectAnswer1.setText(question.getIncorrectAnswers()[0]);
        holder.incorrectAnswer2.setText(question.getIncorrectAnswers()[1]);
        if (question.getIncorrectAnswers().length > 2) {
            // create more text views for more incorrect answers
            for (int i = 2; i < question.getIncorrectAnswers().length; i++) {
                TextView textView = (TextView) LayoutInflater.from(holder.incorrectAnswers.getContext())
                        .inflate(R.layout.gallery_incorrect_text_item, holder.incorrectAnswers, false);
                textView.setText(question.getIncorrectAnswers()[i]);
                holder.incorrectAnswers.addView(textView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return quizQuestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView correctAnswer;
        public TextView incorrectAnswer1;
        public TextView incorrectAnswer2;
        public LinearLayout incorrectAnswers;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            correctAnswer = view.findViewById(R.id.correctAnswer);
            incorrectAnswer1 = view.findViewById(R.id.incorrectAnswer1);
            incorrectAnswer2 = view.findViewById(R.id.incorrectAnswer2);
            incorrectAnswers = view.findViewById(R.id.incorrectQuestionsHolder);
        }
    }
}

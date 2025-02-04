package no.hvl.dat153.quizapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizQuestionAdapter extends RecyclerView.Adapter<QuizQuestionAdapter.ViewHolder> {
    private final List<QuizQuestion> quizQuestions;

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
        holder.imageView.setImageResource(question.getQuestionImage());
        holder.correctAnswer.setText(question.getCorrectAnswer());
        holder.incorrectAnswer1.setText(question.getIncorrectAnswers()[0]);
        holder.incorrectAnswer2.setText(question.getIncorrectAnswers()[1]);
        if (question.getIncorrectAnswers().length > 2) {
            // create more text views for more incorrect answers
            for (int i = 2; i < question.getIncorrectAnswers().length; i++) {
                TextView textView = new TextView(holder.itemView.getContext());
                textView.setText(question.getIncorrectAnswers()[i]);
                textView.setBackgroundResource(R.color.red);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
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

package no.hvl.dat153.quizapp.help;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gallery {
    private static final Gallery instance = new Gallery();
    private final List<QuizQuestion> list;

    private Gallery() {
        this.list = new ArrayList<>();
        Log.d("GallerySingleton", "I have Arrived!");
    }

    public static Gallery getInstance() {
        return instance;
    }

    public void addQuestion(QuizQuestion q) {
        this.list.add(q);
    }

    public void addQuestions(QuizQuestion... questions) {
        this.list.addAll(Arrays.asList(questions));
    }

    public List<QuizQuestion> getQuestions() {
        return this.list;
    }

}

package no.hvl.dat153.quizapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gallery {
    private static Gallery instance = null;
    private final List<QuizQuestion> list;
    private Gallery() {
        this.list = new ArrayList<>();
    }

    public static Gallery getInstance() {
        if (instance == null) {
            instance = new Gallery();
        }
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

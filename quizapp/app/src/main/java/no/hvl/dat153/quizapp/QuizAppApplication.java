package no.hvl.dat153.quizapp;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import no.hvl.dat153.quizapp.help.Gallery;
import no.hvl.dat153.quizapp.help.QuizQuestion;

public class QuizAppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("QuizApp", "onCreate: Adding questions to gallery");

        Gallery.getInstance().addQuestions(
                new QuizQuestion(getResourceUri(R.drawable.feet), "Feet", "Hat", "Toes", "test", "toots", "bird", "cristoffmisbruker", "føtter", "ting", "som", "kan", "funke", "superlang", "tiss", "ligma", "balls"),
                new QuizQuestion(getResourceUri(R.drawable.bird), "fågel", "Feet", "fugl", "Bird", "BIRD", "bird", "B|RD", "B1RD"),
                new QuizQuestion(getResourceUri(R.drawable.cok), "Cok", "Coke", "Coca coke", "Cola", "Pebis", "Bepis", "coca cola"),
                new QuizQuestion(getResourceUri(R.drawable.giiislefoss), "giiislefoss", "mann", "gisle", "foss", "gislefoss", "gissel", "kristian"),
                new QuizQuestion(getResourceUri(R.drawable.pus), "søt", "Feet", "Hatt", "hatt", "hætt", "hætta", "hætten"),
                new QuizQuestion(getResourceUri(R.drawable.little_face), "dumb", "Feet", "Hatt", "hatt", "hætt", "hætta", "hætten"),
                new QuizQuestion(getResourceUri(R.drawable.waaaaaaaaaah), "waaaaaaaah", "Feet", "Hatt", "hatt", "hætt", "hætta", "hætten")
        );
    }

    private Uri getResourceUri(int resId) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + resId);
    }
}

package no.hvl.dat153.quizapp.help;

import java.util.Random;

public class QuizQuestion {
    private final Integer questionImage;
    private final String correctAnswer;
    private final String[] incorrectAnswers;

    private final String imageUri;

    public QuizQuestion(Integer questionImage, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String... incorrectAnswers) {
        this.questionImage = questionImage;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = new String[incorrectAnswers.length + 2];
        this.incorrectAnswers[0] = incorrectAnswer1;
        this.incorrectAnswers[1] = incorrectAnswer2;
        this.imageUri = null;
        if (incorrectAnswers.length - 2 >= 0)
            System.arraycopy(incorrectAnswers, 0, this.incorrectAnswers, 2, incorrectAnswers.length);
    }

    public QuizQuestion(String imageUri, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String... incorrectAnswers) {
        this.questionImage = null;
        this.imageUri = imageUri;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = new String[incorrectAnswers.length + 2];
        this.incorrectAnswers[0] = incorrectAnswer1;
        this.incorrectAnswers[1] = incorrectAnswer2;
        if (incorrectAnswers.length - 2 >= 0)
            System.arraycopy(incorrectAnswers, 0, this.incorrectAnswers, 2, incorrectAnswers.length);
    }

    public String getImageUri() {
        return imageUri;
    }

    public Integer getQuestionImage() {
        return questionImage;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public String[] getAnswers() {
        Random r = new Random();
        String[] answers = new String[3];
        boolean correctAnswerPlaced = false;
        int placedAnswers = 0;
        int usedAnswer = -1;
        while (!correctAnswerPlaced) {
            int index = r.nextInt(3);
            answers[index] = correctAnswer;
            correctAnswerPlaced = true;
        }
        while (placedAnswers < 2) {
            int index = r.nextInt(3);
            while (answers[index] == null) {
                int answerIndex = r.nextInt(incorrectAnswers.length);
                if (answerIndex != usedAnswer) {
                    answers[index] = incorrectAnswers[answerIndex];
                    usedAnswer = answerIndex;
                    placedAnswers++;
                }
            }
        }
        return answers;
    }
}

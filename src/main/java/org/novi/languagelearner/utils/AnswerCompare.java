package org.novi.languagelearner.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;


public class AnswerCompare {
    public record AnswerState(boolean allGood, boolean ignoreAccentGood) {}

    public static AnswerState answerWrongOrRight(String correctAnswer, String userInput) {
       // write all the checks in here. casing and accents should result in correct answer
        return new AnswerState(compareAnswersIgnoreCasing(correctAnswer, userInput),
                compareAnswersIgnoreAccents(correctAnswer, userInput));
    }

    private static boolean compareAnswersIgnoreAccents(String correctAnswer, String userInput) {
        String normalizedCorrectAnswer = normalizeString(correctAnswer);
        String normalizedUserInput = normalizeString(userInput);
        return normalizedCorrectAnswer.equalsIgnoreCase(normalizedUserInput);
    }

    private static boolean compareAnswersIgnoreCasing(String correctAnswer, String userInput) {
        return correctAnswer.equalsIgnoreCase(userInput);
    }

    private static String normalizeString(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    public static String getFeedbackAfterCompare(AnswerState state) {
        return switch (state) {
            case AnswerState(boolean all, boolean accent) when all && accent ->  "100% correct";
            case AnswerState(boolean all, boolean accent) when !all && accent ->  "accents dude";
            case AnswerState(boolean all, boolean accent) when !all && !accent -> "plain wrong, my guy";
            default ->  "invalid";
        };

    }
}
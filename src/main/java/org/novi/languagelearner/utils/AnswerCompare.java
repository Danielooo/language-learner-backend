package org.novi.languagelearner.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

// TODO: Nice to have; implement hard and forgiving comparison settings

public class AnswerCompare {


   public static boolean answerWrongOrRight(String correctAnswer, String userInput) {
       // write all the checks in here. casing and accents should result in correct answer
        if (correctAnswer.equals(userInput)) {
            return true;
        } else if (compareAnswersIgnoreAccents(correctAnswer, userInput)) {
            return true;
        } else if (compareAnswersIgnoreCasing(correctAnswer, userInput)) {
            return true;
        } else {
            return false;
        }
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

    public static String getFeedbackAfterCompare(String correctAnswer, String userInput) {
        if (correctAnswer.equals(normalizeString(userInput))) {
            return "100% Correct!";
        } else if (compareAnswersIgnoreCasing(correctAnswer, userInput)) {
            return "Correct, but watch the casing!";
        } else if (compareAnswersIgnoreAccents(correctAnswer, userInput)) {
            return "We'll take it! But beware of the accents!";
        } else {
            return "Incorrect!";
        }

    }
}
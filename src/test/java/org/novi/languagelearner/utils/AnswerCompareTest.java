package org.novi.languagelearner.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerCompareTest {

    private AnswerCompare answerCompare;

    @BeforeEach
    void setUp() {
        answerCompare = new AnswerCompare();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void answerWrongOrRight() {
        // given
        String correctAnswer1 = "correct";
        String userInput1 = "correct";

        String correctAnswer2 = "cased";
        String userInput2 = "CaSeD";

        String correctAnswer3 = "accented";
        String userInput3 = "äccèntèd";

        String correctAnswer4 = "wrong";
        String userInput4 = "!wronk!";

        // when
        boolean result1 = AnswerCompare.answerWrongOrRight(correctAnswer1, userInput1);
        boolean result2 = AnswerCompare.answerWrongOrRight(correctAnswer2, userInput2);
        boolean result3 = AnswerCompare.answerWrongOrRight(correctAnswer3, userInput3);
        boolean result4 = AnswerCompare.answerWrongOrRight(correctAnswer4, userInput4);

        // then
        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(true, result3);
        assertEquals(false, result4);

    }

    // TODO: Test afmaken
    @Test
    void compareAnswersIgnoreAccents() {
        // given

        // when

        // then
    }



}
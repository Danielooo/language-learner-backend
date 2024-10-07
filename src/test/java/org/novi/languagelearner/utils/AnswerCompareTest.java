package org.novi.languagelearner.utils;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AnswerCompareTest {

    public record TestData(String correctAnswer, String userInput, AnswerCompare.AnswerState output) {
    }

    @Test
    void answerWrongOrRight() {

        // given
        var testData = new ArrayList<TestData>(){
            {
                add(new TestData("correct", "correct", new AnswerCompare.AnswerState(true, true)));
                add(new TestData("cased", "CaSed", new AnswerCompare.AnswerState(true, true)));
                add(new TestData("wrong", "!wronk!", new AnswerCompare.AnswerState(false, false)));
            }
        };

        // when
        for (var item : testData) {
            assertEquals(item.output, AnswerCompare.answerWrongOrRight(item.correctAnswer, item.userInput));
        }
    }

    @Test
    void compareAnswersIgnoreAccents() {
        // given
        var testData = new ArrayList<TestData>(){
            {
                add(new TestData("cased", "CaSeD", new AnswerCompare.AnswerState(true, true)));
                add(new TestData("cased", "cased",  new AnswerCompare.AnswerState(true, true)));
                add(new TestData("cased", "c@$€d", new AnswerCompare.AnswerState(false, false)));
                add(new TestData("correct", "çorreçt", new AnswerCompare.AnswerState(false, true)));
            }
        };

        // when
        for (var item : testData) {
            assertEquals(item.output, AnswerCompare.answerWrongOrRight(item.correctAnswer, item.userInput));
        }
        // then
    }


}
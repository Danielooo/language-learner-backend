package org.novi.languagelearner.dtos.Stats;

import lombok.Data;

@Data
public class StatsOfExerciseResponseDTO {

    private String question;
    private String answer;
    private int timesRight;
    private int timesWrong;


    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTimesRight(int timesRight) {
        this.timesRight = timesRight;
    }

    public void setTimesWrong(int timesWrong) {
        this.timesWrong = timesWrong;
    }
}

package org.novi.languagelearner.dtos.Stats;

import lombok.Data;

@Data
public class StatsOfExerciseResponseDTO {

    private String question;
    private String answer;
    private int timesRight;
    private int timesWrong;

}

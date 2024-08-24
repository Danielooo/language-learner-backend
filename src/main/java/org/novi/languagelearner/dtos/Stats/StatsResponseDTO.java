package org.novi.languagelearner.dtos.Stats;


import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerResponseDTO;

import java.util.List;

public class StatsResponseDTO {

    private ExerciseResponseDTO exerciseResponseDTO;
    private List<UserInputAnswerResponseDTO> userInputAnswersResponseDTOs;
    private int timesRight;
    private int timesWrong;


    public ExerciseResponseDTO getExerciseResponseDTO() {
        return exerciseResponseDTO;
    }

    public void setExerciseResponseDTO(ExerciseResponseDTO exerciseResponseDTO) {
        this.exerciseResponseDTO = exerciseResponseDTO;
    }

    public List<UserInputAnswerResponseDTO> getUserInputAnswersResponseDTOs() {
        return userInputAnswersResponseDTOs;
    }

    public void setUserInputAnswersResponseDTOs(List<UserInputAnswerResponseDTO> userInputAnswersResponseDTOs) {
        this.userInputAnswersResponseDTOs = userInputAnswersResponseDTOs;
    }

    public int getTimesRight() {
        return timesRight;
    }

    public void setTimesRight(int timesRight) {
        this.timesRight = timesRight;
    }

    public int getTimesWrong() {
        return timesWrong;
    }

    public void setTimesWrong(int timesWrong) {
        this.timesWrong = timesWrong;
    }
}

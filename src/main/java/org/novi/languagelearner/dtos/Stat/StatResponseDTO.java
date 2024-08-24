package org.novi.languagelearner.dtos.Stat;


import org.novi.languagelearner.dtos.Unsorted.ExerciseResponseDTO;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.UserInputAnswer;

import java.util.List;

public class StatResponseDTO {

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

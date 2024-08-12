package org.novi.languagelearner.dtos;


import org.novi.languagelearner.entities.Exercise;

public class StatResponseDTO {

    private ExerciseResponseDTO exerciseResponseDTO;
    private String username;
    private int timesRight;
    private int timesWrong;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ExerciseResponseDTO getExerciseResponseDTO() {
        return exerciseResponseDTO;
    }

    public void setExerciseResponseDTO(ExerciseResponseDTO exerciseResponseDTO) {
        this.exerciseResponseDTO = exerciseResponseDTO;
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

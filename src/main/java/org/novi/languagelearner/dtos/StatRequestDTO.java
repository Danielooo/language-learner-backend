package org.novi.languagelearner.dtos;

public class StatRequestDTO {

    private Long exerciseId;

    private int timesRight;
    private int timesWrong;

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
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

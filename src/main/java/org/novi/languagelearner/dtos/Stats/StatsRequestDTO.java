package org.novi.languagelearner.dtos.Stats;

public class StatsRequestDTO {

    private Long exerciseId;

    private String userName;

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

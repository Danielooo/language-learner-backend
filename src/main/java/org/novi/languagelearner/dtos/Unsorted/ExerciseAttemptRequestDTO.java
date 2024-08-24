package org.novi.languagelearner.dtos.Unsorted;

public class ExerciseAttemptRequestDTO {


    private Long exerciseId;
    private String userInput;

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }
}

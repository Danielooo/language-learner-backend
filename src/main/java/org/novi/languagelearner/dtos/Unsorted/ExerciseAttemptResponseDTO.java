package org.novi.languagelearner.dtos.Unsorted;

public class ExerciseAttemptResponseDTO {

    private Long userId;
    private Long exerciseId;

    private String exerciseRepositoryAnswer;
    private boolean isCorrect;

    public String getExerciseRepositoryAnswer() {
        return exerciseRepositoryAnswer;
    }

    public void setExerciseRepositoryAnswer(String exerciseRepositoryAnswer) {
        this.exerciseRepositoryAnswer = exerciseRepositoryAnswer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}

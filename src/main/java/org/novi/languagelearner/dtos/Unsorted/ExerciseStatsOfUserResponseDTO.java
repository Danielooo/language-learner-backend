package org.novi.languagelearner.dtos.Unsorted;

import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.springframework.stereotype.Component;
import java.util.List;

// TODO: Feature build;

@Component
public class ExerciseStatsOfUserResponseDTO {

    private Exercise exercise;
    private List<UserInputAnswer> userInputAnswers;

    private int timesRight;
    private int timesWrong;



    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public List<UserInputAnswer> getUserInputAnswers() {
        return userInputAnswers;
    }

    public void setUserInputAnswers(List<UserInputAnswer> userInputAnswers) {
        this.userInputAnswers = userInputAnswers;
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

package org.novi.languagelearner.dtos;

import org.novi.languagelearner.entities.Exercise;

import java.util.List;

public class GroupRequestDTO {

    private String groupName;

    private List<Exercise> exercises;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}

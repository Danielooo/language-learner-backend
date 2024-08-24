package org.novi.languagelearner.dtos.Group;

import org.novi.languagelearner.entities.Exercise;

import java.util.List;

public class GroupRequestDTO {

    private String groupName;

    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

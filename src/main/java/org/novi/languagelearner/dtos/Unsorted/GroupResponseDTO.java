package org.novi.languagelearner.dtos.Unsorted;

import org.novi.languagelearner.entities.Exercise;

import java.util.List;

public class GroupResponseDTO {

    private Long id;

    private Long userId;

    private String groupName;

    private List<Exercise> exercises;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public void setId(Long id) {
        this.id = id;
    }
}


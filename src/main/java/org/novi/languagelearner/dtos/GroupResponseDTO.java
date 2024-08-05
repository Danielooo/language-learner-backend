package org.novi.languagelearner.dtos;

import org.novi.languagelearner.entities.Excercise;

import java.util.List;

public class GroupResponseDTO {

    private Long userId;

    private String groupName;

    private List<Excercise> excercises;

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

    public List<Excercise> getExcercises() {
        return excercises;
    }

    public void setExcercises(List<Excercise> excercises) {
        this.excercises = excercises;
    }
}


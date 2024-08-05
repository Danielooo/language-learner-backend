package org.novi.languagelearner.dtos;

import org.novi.languagelearner.entities.Excercise;

import java.util.List;

public class GroupRequestDTO {

    private String groupName;

    private List<Excercise> excercises;

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

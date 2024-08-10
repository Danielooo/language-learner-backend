package org.novi.languagelearner.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import java.util.Date;

// TODO: create links to User and Exercise
// TODO: Add List 'stats' containing exercise id, timesright, timeswrong, and lasttime

@Entity
public class UserExerciseStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long exerciseId;



    private int timesRight;
    private int timesWrong;
    private Date lastTimeRight;
    private Date lastTimeWrong;


    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public int getTimesRight() {
        return timesRight;
    }

    public int getTimesWrong() {
        return timesWrong;
    }

    public Date getLastTimeRight() {
        return lastTimeRight;
    }

    public Date getLastTimeWrong() {
        return lastTimeWrong;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setTimesRight(int timesRight) {
        this.timesRight = timesRight;
    }

    public void setTimesWrong(int timesWrong) {
        this.timesWrong = timesWrong;
    }

    public void setLastTimeRight(Date lastTimeRight) {
        this.lastTimeRight = lastTimeRight;
    }

    public void setLastTimeWrong(Date lastTimeWrong) {
        this.lastTimeWrong = lastTimeWrong;
    }
}

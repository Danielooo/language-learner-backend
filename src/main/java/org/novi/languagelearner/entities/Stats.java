package org.novi.languagelearner.entities;

import jakarta.persistence.*;

// TODO: Maybe @Entity annotation not necessary? Is not persisted to database

@Entity
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int timesRight;
    private int timesWrong;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Long getId() {
        return id;
    }



    public int getTimesRight() {
        return timesRight;
    }

    public int getTimesWrong() {
        return timesWrong;
    }





    public void setTimesRight(int timesRight) {
        this.timesRight = timesRight;
    }


    public void setTimesWrong(int timesWrong) {
        this.timesWrong = timesWrong;
    }


}

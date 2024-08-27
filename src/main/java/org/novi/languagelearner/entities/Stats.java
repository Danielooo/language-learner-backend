package org.novi.languagelearner.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

// TODO: Maybe @Entity annotation not necessary? Is not persisted to database

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Stats extends Base {

    private int timesRight;
    private int timesWrong;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

}

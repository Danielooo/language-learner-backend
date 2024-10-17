package org.novi.languagelearner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

// TODO: Maybe delete altogether, is not an entity that needs persisting. Probably responseDTO will be fine

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

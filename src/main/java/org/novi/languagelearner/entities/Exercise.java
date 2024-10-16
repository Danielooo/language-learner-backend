package org.novi.languagelearner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "exercises")
public class Exercise extends Base {

    private String question;
    private String answer;

    public Exercise() {
    }

    public Exercise(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }


    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private Group group;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @Column(name = "user_input_answers")
    private List<UserInputAnswer> userInputAnswers;

}

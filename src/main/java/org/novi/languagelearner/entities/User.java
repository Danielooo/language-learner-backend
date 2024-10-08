package org.novi.languagelearner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// TODO: Add List 'stats' containing exercise id, timesright, timeswrong, and lasttime

// TODO: create check that all usernames are unique

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends Base {

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @ToString.Exclude
    @JsonManagedReference
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Group> groups;

    @Column(nullable = false)
    private boolean isExpired = false;
    @Column(nullable = false)
    private boolean isLocked = false;
    @Column(nullable = false)
    private boolean areCredentialsExpired = false;
    @Column(nullable = false)
    private boolean isEnabled = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference // prevents infinite recursion
    private Photo photo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<UserInputAnswer> userInputAnswers;

}

package org.novi.languagelearner.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// TODO: Add List 'stats' containing exercise id, timesright, timeswrong, and lasttime

// TODO: create check that all usernames are unique

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
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



    public void setId(Long id) {

    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean areCredentialsExpired() {
        return areCredentialsExpired;
    }

    public void setAreCredentialsExpired(boolean areCredentialsExpired) {
        this.areCredentialsExpired = areCredentialsExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAreCredentialsExpired() {
        return areCredentialsExpired;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}

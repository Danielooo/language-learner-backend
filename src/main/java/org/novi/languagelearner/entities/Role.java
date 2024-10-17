package org.novi.languagelearner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "roles")
public class Role extends Base {


    private String roleName;
    private boolean active;
    private String description;

    @ToString.Exclude
    @JsonBackReference
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    public Role(String roleName) {this.roleName = roleName;}

    public Role() {}
}

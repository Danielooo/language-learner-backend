package org.novi.languagelearner.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
// TODO: Ask Frans; is de equals nodig, snap niet in welk geval dit nut heeft
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends Base {


    private String roleName;
    private boolean active;
    private String description;
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();


    public Role(String roleName) {this.roleName = roleName;}

    public Role() {}
}

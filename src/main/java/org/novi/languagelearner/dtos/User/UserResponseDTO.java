package org.novi.languagelearner.dtos.User;

import lombok.Data;
import org.novi.languagelearner.entities.Photo;

import java.util.List;

@Data
public class UserResponseDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private List<String> roles;

    private Photo photo;



}

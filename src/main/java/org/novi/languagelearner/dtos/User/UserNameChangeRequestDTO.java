package org.novi.languagelearner.dtos.User;

import lombok.Data;

@Data
public class UserNameChangeRequestDTO {

    private String currentUserName;
    private String newUserName;
}

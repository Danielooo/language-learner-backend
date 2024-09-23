package org.novi.languagelearner.dtos.Photo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoRequestDTO {

    @NotEmpty(message = "Filename cannot be empty")
    private String fileName;

    @NotEmpty(message = "Filetype cannot be empty")
    private String fileType;

    @NotNull(message = "Photo data cannot be empty")
    private MultipartFile photoData;

    @NotEmpty(message = "Username cannot be empty")
    private String userName;

}

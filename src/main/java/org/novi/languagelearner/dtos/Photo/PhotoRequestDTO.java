package org.novi.languagelearner.dtos.Photo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoRequestDTO {

    private String fileName;

    private String fileType;

    private MultipartFile photoData;

    private String userName;

}

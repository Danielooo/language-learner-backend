package org.novi.languagelearner.dtos.Photo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class PhotoRequestDTO {

    @NotEmpty(message = "Filename cannot be empty")
    private String fileName;

    @NotEmpty(message = "Filetype cannot be empty")
    private String fileType;

    @NotNull(message = "Photo data cannot be empty")
    private MultipartFile photoData;

    private String userName;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public MultipartFile getPhotoData() {
        return photoData;
    }

    public void setPhotoData(MultipartFile photoData) {
        this.photoData = photoData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

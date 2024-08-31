package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.Photo.PhotoRequestDTO;
import org.novi.languagelearner.dtos.Photo.PhotoResponseDTO;
import org.novi.languagelearner.entities.Photo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PhotoMapper {

    private final UserMapper userMapper;

    public PhotoMapper (UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Photo mapToEntity(PhotoRequestDTO mapToEntity) throws IOException {
        Photo photo = new Photo();
        photo.setFilename(mapToEntity.getFileName());
        photo.setFileType(mapToEntity.getFileType());
        photo.setData(mapMultipartFileToByteArray(mapToEntity.getPhotoData()));
        return photo;
    }

    public byte[] mapMultipartFileToByteArray(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }

    public PhotoResponseDTO mapToResponseDTO(Photo photo) {
        PhotoResponseDTO photoResponseDTO = new PhotoResponseDTO();
        photoResponseDTO.setPhotoId(photo.getId());
        photoResponseDTO.setFileName(photo.getFilename());
        photoResponseDTO.setFileType(photo.getFileType());
        photoResponseDTO.setData(photo.getData());
        return photoResponseDTO;
    }
}

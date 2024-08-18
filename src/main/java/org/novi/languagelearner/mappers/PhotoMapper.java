package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.PhotoRequestDTO;
import org.novi.languagelearner.dtos.PhotoResponseDTO;
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

    public Photo mapToEntity(PhotoRequestDTO photoDTO) throws IOException {
        var result = new Photo();
        result.setFilename(photoDTO.getFileName());
        result.setFileType(photoDTO.getFileType());
        result.setData(mapMultipartFileToByteArray(photoDTO.getPhotoData()));
        return result;
    }

    public byte[] mapMultipartFileToByteArray(MultipartFile multipartFile) throws IOException {
        return multipartFile.getBytes();
    }

    public PhotoResponseDTO mapToResponseDTO(Photo photo) {
        var photoResponseDTO = new PhotoResponseDTO();
        photoResponseDTO.setPhotoId(photo.getId());
        photoResponseDTO.setFileName(photo.getFilename());
        photoResponseDTO.setFileType(photo.getFileType());
        photoResponseDTO.setData(photo.getData());
        return photoResponseDTO;
    }
}

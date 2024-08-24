package org.novi.languagelearner.services;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.dtos.Unsorted.PhotoRequestDTO;
import org.novi.languagelearner.dtos.Unsorted.UserResponseDTO;
import org.novi.languagelearner.entities.Photo;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.PhotoMapper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.repositories.PhotoRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class PhotoService {


    private final PhotoRepository photoRepository;
    private final UserMapper userMapper;
    private UserRepository userRepository;
    private PhotoMapper photoMapper;

    public PhotoService(UserRepository userRepository, PhotoMapper photoMapper, PhotoRepository photoRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.photoMapper = photoMapper;
        this.photoRepository = photoRepository;
        this.userMapper = userMapper;
    }

    // no return, just ok or exception; separate getPhotoByUserName to check if photo was persisted

    @Transactional
    public UserResponseDTO uploadPhoto(PhotoRequestDTO photoRequestDTO) throws IOException {

        Optional<User> userOptional = userRepository.findByUserName(photoRequestDTO.getUserName());

        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        }

        User user = userOptional.get();
        try {
            Photo newPhoto = photoMapper.mapToEntity(photoRequestDTO);

            newPhoto.setUser(user);

            user.setPhoto(newPhoto);

            UserResponseDTO userResponseDTO = userMapper.mapToResponseDTO(userRepository.save(user));
            return userResponseDTO;
        } catch (BadRequestException e) {
            throw new IOException("Photo could not be persisted");
        }
    }
}















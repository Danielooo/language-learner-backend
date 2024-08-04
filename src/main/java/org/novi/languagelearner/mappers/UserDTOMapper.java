//package org.novi.languagelearner.mappers;
//
//
//import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
//import org.novi.languagelearner.dtos.UserRequestDTO;
//import org.novi.languagelearner.dtos.UserResponseDTO;
//import org.novi.languagelearner.models.UserModel;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserDTOMapper {
//    // TODO: change this to UserMapper
//
//    // This method maps a UserRequestDTO object to a UserModel object
//    public UserModel mapToModel(UserRequestDTO userDTO) {
//        var result = new UserModel(-1L);
//        result.setUserName(userDTO.getUserName());
//        result.setPassword(userDTO.getPassword());
//        return result;
//    }
//
//    public UserModel mapToModel(UserChangePasswordRequestDTO userDTO, Long id) {
//        var result = new UserModel(id);
//        result.setPassword(userDTO.getPassword());
//        return result;
//    }
//
//    // UserModel to a UserResponseDTO
//    public UserResponseDTO mapToResponseDTO(UserModel userModel) {
//        var userResponseDTO = new UserResponseDTO();
//        userResponseDTO.setId(userModel.getId());
//        userResponseDTO.setUsername(userModel.getUserName());
//        // just for testing purposes, unsafe to return password
//        userResponseDTO.setPassword(userModel.getPassword());
//
//        return userResponseDTO;
//    }
//}

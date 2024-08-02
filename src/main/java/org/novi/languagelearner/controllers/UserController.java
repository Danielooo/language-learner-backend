package nl.novi.les16JWT.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
import org.novi.languagelearner.dtos.UserDTOMapper;
import org.novi.languagelearner.dtos.UserRequestDTO;
import org.novi.languagelearner.helpers.UrlHelper;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserDTOMapper userDTOMapper;
    private final UserService userService;
    private final HttpServletRequest request;

    public UserController(UserDTOMapper userDTOMapper, UserService userService, HttpServletRequest request) {
        this.userDTOMapper = userDTOMapper;
        this.userService = userService;
        this.request = request;
    }

    @PostMapping("/users")
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO)
    {
        var userModel = userDTOMapper.mapToModel(userDTO);
        userModel.setEnabled(true);
        if(!userService.createUser(userModel, userDTO.getRoles())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, userModel.getId())).build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> ChangePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordRequestDTO userDTO)
    {
        var userModel = userDTOMapper.mapToModel(userDTO, id);
        if(!userService.updatePassword(userModel)) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok().build();
    }
}

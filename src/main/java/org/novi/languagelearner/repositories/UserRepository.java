package org.novi.languagelearner.repositories;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    Optional<User> findByUserName(String username);

    // find User by username and retrieve the exercises (by exerciseId) that the user has done. User has one-to-many relationship with Exercise
//    Optional<User> findByUserNameAndExerciseId(String username, Long exerciseId);

    Optional<User> findByUserNameAndPassword(String username, String password);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByUserName(String username);

    List<Long> findGroupIdsByUserName(String userName);


    List<User> findByLastNameContainingIgnoreCaseAndRoles_RoleName(String lastName, String roleName);
}

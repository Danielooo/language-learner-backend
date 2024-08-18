package org.novi.languagelearner.repositories;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    Optional<User> findByUserName(String username);

    Optional<User> findByUserNameAndPassword(String username, String password);

    Optional<User> findUserById(Long id);
}

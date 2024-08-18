package org.novi.languagelearner.repositories;

import org.novi.languagelearner.entities.Photo;
import org.novi.languagelearner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PhotoRepository extends JpaRepository<Photo, Long> {

}

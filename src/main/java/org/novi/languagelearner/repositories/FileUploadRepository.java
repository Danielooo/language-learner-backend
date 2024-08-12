package org.novi.languagelearner.repositories;

import org.novi.languagelearner.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<Photo, String> {


}

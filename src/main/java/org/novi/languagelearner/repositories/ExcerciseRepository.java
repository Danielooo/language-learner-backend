package org.novi.languagelearner.repositories;

import org.novi.languagelearner.entities.Excercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcerciseRepository extends JpaRepository<Excercise, Long> {

}

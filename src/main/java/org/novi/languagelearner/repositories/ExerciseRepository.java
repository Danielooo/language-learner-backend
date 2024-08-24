package org.novi.languagelearner.repositories;

import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {



    @Query("SELECT e FROM Exercise e WHERE e.id = :exerciseId")
    Optional<Exercise> findExerciseWithoutUserInputAnswersById(@Param("exerciseId") Long exerciseId);


//    Optional<Exercise> findExerciseWithoutUserInputAnswersById(@Param("id") Long exerciseId);


}

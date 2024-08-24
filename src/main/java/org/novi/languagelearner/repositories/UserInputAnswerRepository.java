package org.novi.languagelearner.repositories;

import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInputAnswerRepository extends JpaRepository<UserInputAnswer, Long> {


    List<UserInputAnswer> findUserInputAnswersByUserAndExerciseId(User user, Long exerciseId);
}

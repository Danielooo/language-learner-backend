package org.novi.languagelearner.repositories;

import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByGroupName(String groupName);
    List<Group> findByExercises(Exercise exercise);
}

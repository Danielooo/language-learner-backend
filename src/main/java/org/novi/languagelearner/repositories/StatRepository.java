package org.novi.languagelearner.repositories;

import org.novi.languagelearner.entities.Stat;
import org.novi.languagelearner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatRepository extends JpaRepository<Stat, Long> {

    Optional<Stat> findStatById(Long id);

    List<Stat> findAllByUser_UserName(String username);

    List<Stat> findAllByUser_UserNameInAndExerciseIdIn(List<String> username, List<Long> exerciseId);
    List<Stat> findAllByUser_UserNameIn(List<String> usernames);
    List<Stat> findAllByExerciseIdIn(List<Long> exerciseIds);

}

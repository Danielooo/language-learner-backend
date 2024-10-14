package org.novi.languagelearner.repositories;

import org.novi.languagelearner.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByUser_UserName(String userName);


    Optional<Group> findById(Long id);

    List<Group> findAllByGroupName(String groupName);

    // g.* alles van group
    @Query("SELECT DISTINCT g FROM Group g " +
            "JOIN g.exercises e " +
            "JOIN e.userInputAnswers uia " +
            "WHERE g.id IN :groupIds " +
            "AND uia.editDate BETWEEN :userInputStartTime AND :userInputEndTime")
    List<Group> findStatsByGroupIdsAndTimeRange(
            @Param("groupIds") List<Long> groupIds,
            @Param("userInputStartTime") LocalDateTime userInputStartTime,
            @Param("userInputEndTime") LocalDateTime userInputEndTime
        );

    boolean existsByGroupName(String groupName);
}

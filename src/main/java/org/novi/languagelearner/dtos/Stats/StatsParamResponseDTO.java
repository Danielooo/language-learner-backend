package org.novi.languagelearner.dtos.Stats;

import lombok.Data;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StatsParamResponseDTO {

    // Owner
    private String userName;

    // Group
    private Long groupId;
    private String groupName;

    // Exercise
    private Long exerciseId;
    private String exerciseQuestion;
    private String exerciseAnswer;

    // Results within param selection
    private int timesRight;
    private int timesWrong;
    private int totalAttempts;

    // Last attempt result
    private boolean lastResult;
    private LocalDateTime attemptDateTime;

    // Params of client
    private List<Long> groupIds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}

package org.novi.languagelearner.dtos.Stats;

import lombok.Data;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerResponseDTO;

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

    private List<UserInputAnswerResponseDTO> userInputRecords;



}

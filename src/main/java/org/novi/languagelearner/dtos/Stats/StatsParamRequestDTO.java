package org.novi.languagelearner.dtos.Stats;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

// TODO: kill, not working
@Data
public class StatsParamRequestDTO {

    @NotNull(message = "username cannot be a null value")
    private String userName;

    @NotEmpty(message = "Group ID's cannot be empty")
    private List<Long> groupIds;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime userInputStartTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime userInputEndTime;
}

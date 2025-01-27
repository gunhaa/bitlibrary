package bitcopark.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassScheduleRequestDTO {
    private LocalDate recruitmentStartDt;
    private LocalDate recruitmentEndDt;
    private LocalDate startDt;
    private LocalDate endDt;
    private Long maxParticipant;
}

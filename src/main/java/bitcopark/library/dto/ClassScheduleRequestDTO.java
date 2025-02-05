package bitcopark.library.dto;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.clazz.ClassSchedule;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClassScheduleRequestDTO {
    private LocalDateTime recruitmentStartDt;
    private LocalDateTime recruitmentEndDt;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private Long maxParticipant;

    public ClassSchedule toEntity(Board board) {
        return ClassSchedule.builder()
                .recruitmentStartDate(recruitmentStartDt)
                .recruitmentEndDate(recruitmentEndDt)
                .startDate(startDt)
                .endDate(endDt)
                .maxParticipants(maxParticipant)
                .board(board)
                .build();
    }
}

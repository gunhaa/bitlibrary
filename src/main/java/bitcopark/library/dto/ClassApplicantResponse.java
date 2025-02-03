package bitcopark.library.dto;

import bitcopark.library.entity.clazz.ClassApplicant;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassApplicantResponse {

    private Long id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime recruitmentStartDate;
    private LocalDateTime recruitmentEndDate;

    public ClassApplicantResponse(ClassApplicant classApplicant) {
        this.id = classApplicant.getId();
        this.title = classApplicant.getBoard().getTitle();
        this.startDate = classApplicant.getBoard().getClassSchedule().getStartDate();
        this.endDate = classApplicant.getBoard().getClassSchedule().getEndDate();
        this.recruitmentStartDate = classApplicant.getBoard().getClassSchedule().getRecruitmentStartDate();
        this.recruitmentEndDate = classApplicant.getBoard().getClassSchedule().getRecruitmentEndDate();
    }
}

package bitcopark.library.controller.intro;

import bitcopark.library.entity.orphanTable.CalendarType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CalendarDTO {

    private int id; // 캘랜더 id
    private String calendarName; // 일정명
    private LocalDate calendarStart; // 시작일
    private LocalDate endDt; // 종료일
    private LocalDate rstartDt; // 모집 시작일
    private LocalDate rendDt; // 모집종료일
    private CalendarType calendarType; // 일정종류(1=공휴일, 2=휴관일, 3=행사)

}

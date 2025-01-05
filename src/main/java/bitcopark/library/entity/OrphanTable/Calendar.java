package bitcopark.library.entity.OrphanTable;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Calendar {

    @Id
    @GeneratedValue
    @Column(name = "calendar_id")
    private Long id;

    private String calendarName;

    @Builder.Default
    private LocalDate startDt = LocalDateTime.now().toLocalDate();

    private LocalDate endDt;

    @Enumerated(EnumType.STRING)
    private CalendarType calendarType;

}

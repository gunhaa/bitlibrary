package bitcopark.library.service.orphanTable;

import bitcopark.library.entity.orphanTable.Calendar;
import bitcopark.library.entity.orphanTable.CalendarType;
import bitcopark.library.repository.orphanTable.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository scheduleRepository;

    @Transactional
    public Calendar registerSchedule(String scheduleName, LocalDate scheduleEnd, CalendarType scheduleType){
        Calendar schedule = Calendar.builder()
                .calendarName(scheduleName)
                .endDt(scheduleEnd)
                .calendarType(scheduleType)
                .build();
        return scheduleRepository.save(schedule);
    }

}

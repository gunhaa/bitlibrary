package bitcopark.library.service.OrphanTable;

import bitcopark.library.entity.OrphanTable.Calendar;
import bitcopark.library.entity.OrphanTable.CalendarType;
import bitcopark.library.repository.OrphanTable.CalendarRepository;
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

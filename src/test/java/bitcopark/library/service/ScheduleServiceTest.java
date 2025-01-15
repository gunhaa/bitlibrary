package bitcopark.library.service;

import bitcopark.library.entity.orphanTable.Calendar;
import bitcopark.library.entity.orphanTable.CalendarType;
import bitcopark.library.repository.OrphanTable.CalendarRepository;
import bitcopark.library.service.OrphanTable.CalendarService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired
    CalendarService scheduleService;

    @Autowired
    CalendarRepository scheduleRepository;

    @Test
    @Commit
    public void 스케쥴_등록(){
        //given
        String scheduleName = "스케쥴_이름";
        LocalDate scheduleEnd = LocalDate.of(2024, 12, 31);
        CalendarType scheduleType = CalendarType.HOLIDAY;

        //when
        scheduleService.registerSchedule(scheduleName, scheduleEnd, scheduleType);

        //then
        List<Calendar> findScheduleNameList = scheduleRepository.findByCalendarName(scheduleName);

        List<Calendar> findScheduleTypeList = scheduleRepository.findByCalendarType(scheduleType);

        Assertions.assertThat(findScheduleNameList.size()).isEqualTo(1);

        Assertions.assertThat(findScheduleTypeList.size()).isEqualTo(1);

    }

}
package bitcopark.library.controller.intro;

import bitcopark.library.entity.orphanTable.Calendar;
import bitcopark.library.service.OrphanTable.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IntroRestController {

    private final CalendarService calendarService;

    @PostMapping(value="/intro/guide/calendar",produces = "application/json; charset=UTF-8")
    public List<Calendar> getDateCalendar(@RequestBody CalendarDTO calendarDTO) {
        System.out.println("calendarDTO.getCalendarStart() = " + calendarDTO.getCalendarStart());
        // 2025-01-05
        System.out.println("calendarDTO.getCalendarType() = " + calendarDTO.getCalendarType());
        // EVENT
        return null;
    }

}

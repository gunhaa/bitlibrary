package bitcopark.library.repository.OrphanTable;

import bitcopark.library.entity.OrphanTable.Calendar;
import bitcopark.library.entity.OrphanTable.CalendarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findByCalendarName(String calendarName);

    List<Calendar> findByCalendarType(CalendarType calendarType);

}

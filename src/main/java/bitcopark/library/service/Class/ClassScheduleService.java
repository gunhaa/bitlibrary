package bitcopark.library.service.Class;

import bitcopark.library.dto.ClassScheduleRequestDTO;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.clazz.ClassSchedule;
import bitcopark.library.repository.clazz.ClassScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassScheduleService {

    private final ClassScheduleRepository classScheduleRepository;

    @Transactional
    public ClassSchedule registClassSchedule(Board board, ClassScheduleRequestDTO classScheduleRequestDTO) {
        return classScheduleRepository.save(classScheduleRequestDTO.toEntity(board));
    }
}

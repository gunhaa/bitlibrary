package bitcopark.library.service.Member;

import bitcopark.library.repository.Member.BookRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookRequestService {

    private final BookRequestRepository bookRequestRepository;


}

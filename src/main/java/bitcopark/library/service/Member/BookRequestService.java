package bitcopark.library.service.Member;

import bitcopark.library.controller.search.BookRequestCondition;
import bitcopark.library.controller.search.BookRequestResponseDto;
import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.entity.member.BookRequestApprove;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Member.BookRequestRepository;
import bitcopark.library.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookRequestService {

    private final BookRequestRepository bookRequestRepository;
    private final MemberRepository memberRepository;

    public BookRequest createBookRequest(BookRequestCondition bookRequestCondition){
        Member findMember = memberRepository.findById(bookRequestCondition.getMemberId()).orElseThrow(() -> new IllegalArgumentException("invalid memberId"));
        BookRequest bookRequest = BookRequest.builder()
                .RequestTitle(bookRequestCondition.getRequestTitle())
                .bookTitle(bookRequestCondition.getBookTitle())
                .publisher(bookRequestCondition.getBookPublisher())
                .author(bookRequestCondition.getBookAuthor())
                .bookRequestApprove(BookRequestApprove.W)
                .publicationDate(bookRequestCondition.getBookPublicationDate())
                .opinion(bookRequestCondition.getOpinion())
                .member(findMember)
                .build();
        bookRequestRepository.save(bookRequest);
        return bookRequest;
    }

    @Transactional
    public BookRequestResponseDto registerBookRequest(BookRequestCondition bookRequestCondition){
        // 게시글 변환로직

        // 쿼리 실행
        try {
            createBookRequest(bookRequestCondition);
            return new BookRequestResponseDto(true, "Book Request Success");
        } catch (Exception e){
            return new BookRequestResponseDto(false, "Book Request fail");
        }

    }

    public BookRequestListDto getBookRequestList(Pageable page) {
        bookRequestRepository.getBookRequestPage(page);
        return null;
    }
}

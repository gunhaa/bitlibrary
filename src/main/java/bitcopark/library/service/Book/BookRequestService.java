package bitcopark.library.service.Book;

import bitcopark.library.controller.book.BookRequestCondition;
import bitcopark.library.controller.book.BookRequestResponseDto;
import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.entity.member.BookRequestApprove;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Book.BookRequestRepository;
import bitcopark.library.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookRequestService {

    private final BookRequestRepository bookRequestRepository;
    private final MemberRepository memberRepository;

    public BookRequest createBookRequest(Long memberId, String isbn, String bookTitle, String bookPublisher,
                                         String bookAuthor, BookRequestApprove bookRequestApprove,
                                         LocalDate publicationDate, String opinion) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("invalid memberId"));

        BookRequest bookRequest = BookRequest.builder()
                .isbn(isbn)
                .bookTitle(bookTitle)
                .publisher(bookPublisher)
                .author(bookAuthor)
                .bookRequestApprove(bookRequestApprove != null ? bookRequestApprove : BookRequestApprove.W)
                .publicationDate(publicationDate)
                .opinion(opinion)
                .member(findMember)
                .build();

        bookRequestRepository.save(bookRequest);

        return bookRequest;
    }


    public BookRequest createBookRequest(BookRequestCondition bookRequestCondition){
        Member findMember = memberRepository.findById(bookRequestCondition.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("invalid memberId"));

        BookRequest bookRequest = BookRequest.builder()
                .isbn(bookRequestCondition.getIsbn())
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

    public Page<BookRequestPageDto> getBookRequestPage(Pageable pageable) {
        return bookRequestRepository.getBookRequestPage(pageable);
    }

    @Transactional
    public BookRequestDetailDto getBookRequestDetailsByIsbn(String isbn) {
        // logic
        return bookRequestRepository.getBookRequestDetail(isbn);
    }
}

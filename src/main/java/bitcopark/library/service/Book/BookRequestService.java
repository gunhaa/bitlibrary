package bitcopark.library.service.Book;

import bitcopark.library.controller.book.BookDeleteDto;
import bitcopark.library.controller.book.BookRequestCondition;
import bitcopark.library.controller.book.BookRequestResponseDto;
import bitcopark.library.dto.BookApplicationResponse;
import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.entity.member.BookRequestApprove;
import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.book.BookRepository;
import bitcopark.library.repository.book.BookRequestRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookRequestService {

    private final BookRequestRepository bookRequestRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

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

        Member findMember = memberRepository.findByEmail(bookRequestCondition.getEmail())
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
        try {
            boolean isExist = bookRepository.existsByIsbn(bookRequestCondition.getIsbn());
            if(isExist){
                return new BookRequestResponseDto(false, "Book exist");
            }
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

    @Transactional
    public ResponseEntity<?> deleteBookRequest(BookDeleteDto bookDeleteDto, LoginMemberDTO loginMember) {

        if(loginMember.getRole().equals("ROLE_ADMIN")){
            System.out.println("관리자여서 삭제 실행");
            bookRequestRepository.deleteByIsbn(bookDeleteDto.getIsbn());
            return new ResponseEntity<>("bookRequest delete success", HttpStatus.OK);
        }

        BookRequest findByEmail = bookRequestRepository.findByEmail(loginMember.getEmail()).orElseThrow(() -> new IllegalArgumentException("not valid email"));
        BookRequest findByIsbn = bookRequestRepository.findByIsbn(bookDeleteDto.getIsbn()).orElseThrow(() -> new IllegalArgumentException("not valid isbn"));

        if(findByEmail == findByIsbn){
            System.out.println("같은 객체가 발견되어 삭제 실행");
            bookRequestRepository.deleteByIsbn(bookDeleteDto.getIsbn());
            return new ResponseEntity<>("bookRequest delete success", HttpStatus.OK);
        }

        return new ResponseEntity<>("not valid request", HttpStatus.BAD_REQUEST);
    }

    public Page<BookApplicationResponse> getBookApplications(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        Page<BookRequest> bookApplications = bookRequestRepository.findByMember(member, pageable);

        List<BookApplicationResponse> dtoList = bookApplications.getContent().stream().map(BookApplicationResponse::new).toList();

        return new PageImpl<>(dtoList, pageable, bookApplications.getTotalElements());
    }
}

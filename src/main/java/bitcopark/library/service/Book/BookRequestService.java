package bitcopark.library.service.Book;

import bitcopark.library.controller.book.BookApproveDto;
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
                return new BookRequestResponseDto(false, "Book already exist");
            }
            createBookRequest(bookRequestCondition);
            return new BookRequestResponseDto(true, "Book Request Success");
        } catch (Exception e){
            return new BookRequestResponseDto(false, "Book Request fail " + e.getMessage());
        }
    }

    public Page<BookRequestPageDto> getBookRequestPage(Pageable pageable) {
        return bookRequestRepository.getBookRequestPage(pageable);
    }

    @Transactional
    public BookRequestDetailDto getBookRequestDetailsByIsbn(String isbn) {
        return bookRequestRepository.getBookRequestDetail(isbn);
    }

    @Transactional
    public ResponseEntity<?> updateBookRequest(BookRequestCondition updateCondition, LoginMemberDTO loginMember) {

        if(loginMember.getEmail().equals(updateCondition.getEmail())){

            if(bookRepository.existsByIsbn(updateCondition.getIsbn()) && !updateCondition.getIsbn().equals(updateCondition.getPrevIsbn())){
               return new ResponseEntity<>("bookRequest isbn exist" ,HttpStatus.BAD_REQUEST);
            }

            BookRequest findByIsbn = bookRequestRepository.findByIsbn(updateCondition.getPrevIsbn()).orElseThrow(() -> new IllegalArgumentException("not valid isbn"));
            findByIsbn.bookRequestStatusUpdate(updateCondition);
            return new ResponseEntity<>("bookRequest update success", HttpStatus.OK);
        }

        return new ResponseEntity<>("not valid request", HttpStatus.BAD_REQUEST);
    }


    @Transactional
    public ResponseEntity<?> approveStatusChangeBookRequest(BookApproveDto bookApproveDto, LoginMemberDTO loginMember) {

        if(loginMember.getRole().equals("ROLE_ADMIN")){
            BookRequest findByIsbn = bookRequestRepository.findByIsbn(bookApproveDto.getIsbn()).orElseThrow(() -> new IllegalArgumentException("not valid isbn"));
            String approvalStatus = bookApproveDto.getApproval();
            findByIsbn.bookApprovalStatusChange(approvalStatus);
            return new ResponseEntity<>("bookRequest status change success", HttpStatus.OK);
        }

        BookRequest findByEmail = bookRequestRepository.findByEmail(loginMember.getEmail()).orElseThrow(() -> new IllegalArgumentException("not valid email"));
        BookRequest findByIsbn = bookRequestRepository.findByIsbn(bookApproveDto.getIsbn()).orElseThrow(() -> new IllegalArgumentException("not valid isbn"));

        if(findByEmail == findByIsbn){
            String approvalStatus = bookApproveDto.getApproval();
            findByEmail.bookApprovalStatusChange(approvalStatus);
            return new ResponseEntity<>("bookRequest status change success", HttpStatus.OK);
        }

        return new ResponseEntity<>("not valid request", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<?> deleteBookRequest(BookDeleteDto bookDeleteDto, LoginMemberDTO loginMember) {

        if(loginMember.getRole().equals("ROLE_ADMIN")){
            bookRequestRepository.deleteByIsbn(bookDeleteDto.getIsbn());
            return new ResponseEntity<>("bookRequest delete success", HttpStatus.OK);
        }

        try {

            BookRequest findByEmail = bookRequestRepository.findByEmail(loginMember.getEmail()).orElseThrow(() -> new IllegalArgumentException("not valid email"));
            BookRequest findByIsbn = bookRequestRepository.findByIsbn(bookDeleteDto.getIsbn()).orElseThrow(() -> new IllegalArgumentException("not valid isbn"));

            if (findByEmail == findByIsbn) {
                bookRequestRepository.deleteByIsbn(bookDeleteDto.getIsbn());
                return new ResponseEntity<>("bookRequest delete success", HttpStatus.OK);
            }

        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
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

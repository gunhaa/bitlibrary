package bitcopark.library.service.Book;

import bitcopark.library.dto.BookLoanHistoryResponse;
import bitcopark.library.dto.BookLoanResponse;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookBorrow;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.book.BookBorrowRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookBorrowService {

    private final BookBorrowRepository bookBorrowRepository;
    private final MemberRepository memberRepository;

    
    @Transactional
    public BookBorrow registerBookRental(Member member, Book book){
        
        Boolean isBorrowed = bookBorrowRepository.existsByBook(book);

        if(isBorrowed){
            return null;
        }

        BookBorrow bookBorrow = BookBorrow.builder()
                .member(member)
                .book(book)
                .build();
        return bookBorrowRepository.save(bookBorrow);
    }

    public List<BookLoanResponse> getCurrentlyBorrowedBooks(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        return bookBorrowRepository.findByReturnDateIsNullAndMember(member)
                .stream().map(BookLoanResponse::new).toList();
    }

    public Page<BookLoanHistoryResponse> getBookLoanHistory(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        Page<BookBorrow> loanHistory = bookBorrowRepository.findByReturnDateIsNotNullAndMember(member, pageable);

        List<BookLoanHistoryResponse> dtoList = loanHistory.getContent().stream().map(BookLoanHistoryResponse::new).toList();

        return new PageImpl<>(dtoList, pageable, loanHistory.getTotalElements());
    }
}

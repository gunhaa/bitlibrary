package bitcopark.library.service.Book;

import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookBorrow;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.book.BookBorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookBorrowService {

    private final BookBorrowRepository bookBorrowRepository;

    
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

}

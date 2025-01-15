package bitcopark.library.service.Book;

import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookBorrow;
import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.repository.Book.BookBorrowRepository;
import bitcopark.library.service.Member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class BookBorrowServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookBorrowService bookBorrowService;

    @Autowired
    private BookBorrowRepository bookBorrowRepository;

    private Member member;
    private Book book;

    @Test
    @Commit
    public void 책_대여(){

        //when
        bookBorrowService.registerBookRental(member, book);

        //then
        List<BookBorrow> bookBorrowList = bookBorrowRepository.findByMember(member);
        Assertions.assertThat(bookBorrowList.size()).isEqualTo(1);

    }


    @BeforeEach
    public void 회원등록_책등록(){
        //given
        String email = "test@email.com";
        String name = "member1";
        String phoneNumber = "01012345678";
        MemberGender gender = MemberGender.MALE;
        int birth = 911111;
        String zipcode = "12345";
        String detailed = "D동";
        Address address = new Address(zipcode, detailed);
        member = memberService.joinMember(email, name, phoneNumber, gender, birth, address);

        String author = "저자";
        String title = "책제목";
        String publisher = "출판사";
        String publicationDate = "20121013";
        String isbn = "11111";
        String thumbnail = "썸네일1";
        BookState bookState = BookState.I;
        BookSupple bookSupple = BookSupple.N;
        book = bookService.registerNewBook(author, title, publisher, publicationDate, isbn, thumbnail, bookState, bookSupple);
    }

}
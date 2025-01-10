package bitcopark.library.repository.Book;

import bitcopark.library.entity.Book.QBook;
import bitcopark.library.entity.Book.QBookBorrow;
import bitcopark.library.entity.Book.QBookReservation;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookDto> findAllBooks() {
        QBook book = QBook.book;
        QBookBorrow bookBorrow = QBookBorrow.bookBorrow;
        QBookReservation bookReservation = QBookReservation.bookReservation;

//        return queryFactory.select(new QBookDto(
//                    book.author,
//                    book.title,
//                    book.publisher,
//                    book.publicationDate,
//                    book.isbn,
//                    book.thumbnail,
//                    book.state,
//                    book.supple,
//                        bookBorrow.count().eq(book.id)))
//                    .from(book).fetchJoin(bookBorrow)
//                    .fetch();

        return null;
    }


}

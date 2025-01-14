package bitcopark.library.repository.Book;

import bitcopark.library.entity.Book.QBook;
import bitcopark.library.entity.Book.QBookBorrow;
import bitcopark.library.entity.Book.QBookReservation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookSearchDto> findAllBooks(BookSearchCondition bookSearchCondition) {
        QBook book = QBook.book;
        QBookBorrow bookBorrow = QBookBorrow.bookBorrow;
        QBookReservation bookReservation = QBookReservation.bookReservation;
        // 수정필요
        return queryFactory.select(new QBookSearchDto(
                        book.author,
                        book.title,
                        book.publisher,
                        book.publicationDate,
                        book.isbn,
                        book.thumbnail,
                        book.bookState,
                        book.bookSupple,
                        bookReservation.count(),
                        (JPAExpressions
                                .select(bookBorrow.returnDueDate)
                                .from(bookBorrow)
                                .where(bookBorrow.book.id.eq(book.id).and(bookBorrow.returnDate.isNull()))
                        )
                ))
                .from(book)
                .leftJoin(book.bookBorrowList, bookBorrow)
                .leftJoin(book.bookReservationList, bookReservation)
                .where(
                        keyEq(bookSearchCondition)
                )
                .fetch();

        /*
                return queryFactory.select(new QBookSearchDto(
                        book.author,
                        book.title,
                        book.publisher,
                        book.publicationDate,
                        book.isbn,
                        book.thumbnail,
                        book.bookState,
                        book.bookSupple,
//                        (JPAExpressions
//                                .select(bookBorrow.count())
//                                .from(bookBorrow)
//                                .where(bookBorrow.book.id.eq(book.id))
//                        ),
                        bookBorrow.count(),
                        bookBorrow.returnDueDate
                        ),
//                        (JPAExpressions
//                                .select(bookFavorite.count())
//                                .from(bookFavorite)
//                                .where(bookFavorite.book.id.eq(book.id))
//                        )
                    bookFavorite.count()
                )
                .from(book)
                .leftJoin(book.bookBorrowList, bookBorrow)
                .leftJoin(book.bookFavoriteList, bookFavorite)
                .where(
                        keyEq(bookSearchCondition)
                )
                .fetch();


        */

    }

    private BooleanExpression keyEq(BookSearchCondition bookSearchCondition) {
        return switch (bookSearchCondition.getKey()) {
            case ta -> QBook.book.title.contains(bookSearchCondition.getQuery()).or(QBook.book.author.contains(bookSearchCondition.getQuery()));
            case t -> QBook.book.title.contains(bookSearchCondition.getQuery());
            case a -> QBook.book.author.contains(bookSearchCondition.getQuery());
        };

    }


}

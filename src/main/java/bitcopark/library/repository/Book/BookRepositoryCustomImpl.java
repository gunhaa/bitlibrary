package bitcopark.library.repository.Book;

import bitcopark.library.controller.search.BookSearchDetailCondition;
import bitcopark.library.entity.book.QBook;
import bitcopark.library.entity.book.QBookBorrow;
import bitcopark.library.entity.book.QBookReservation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookSearchDto> findSearchConditionBooks(BookSearchCondition bookSearchCondition) {
        QBook book = QBook.book;
        QBookBorrow bookBorrow = QBookBorrow.bookBorrow;
        QBookReservation bookReservation = QBookReservation.bookReservation;
        // 수정필요
        return getBookSearchDtoJPAQuery(book, bookReservation, bookBorrow)
                .where(
                        keyContain(bookSearchCondition)
                )
                .fetch();
    }

    @Override
    public List<BookSearchDto> findSearchDetailConditionBooks(BookSearchDetailCondition bookSearchDetailCondition) {
        QBook book = QBook.book;
        QBookBorrow bookBorrow = QBookBorrow.bookBorrow;
        QBookReservation bookReservation = QBookReservation.bookReservation;

        return getBookSearchDtoJPAQuery(book, bookReservation, bookBorrow)
                .where(
                    queryContain(bookSearchDetailCondition.getQuery()),
                    authorContain(bookSearchDetailCondition.getAuthor()),
                    pubContain(bookSearchDetailCondition.getPub()),
                    pubGoe(bookSearchDetailCondition.getStartYear()),
                    pubLoe(bookSearchDetailCondition.getEndYear())
                )
                .limit(bookSearchDetailCondition.getSize())
                .fetch();
    }

    private BooleanExpression queryContain(String query) {
        return StringUtils.hasText(query) ? QBook.book.title.contains(query) : null;
    }

    private BooleanExpression authorContain(String author) {
        return StringUtils.hasText(author) ? QBook.book.author.contains(author) : null;
    }

    private BooleanExpression pubContain(String pub) {
        return StringUtils.hasText(pub) ? QBook.book.publisher.contains(pub) : null;
    }

    private BooleanExpression pubLoe(String endYear) {
        return StringUtils.hasText(endYear) ? QBook.book.publicationDate.loe(endYear) : null;
    }

    private BooleanExpression pubGoe(String startYear) {
        return StringUtils.hasText(startYear) ? QBook.book.publicationDate.goe(startYear) : null;
    }

    private JPAQuery<BookSearchDto> getBookSearchDtoJPAQuery(QBook book, QBookReservation bookReservation, QBookBorrow bookBorrow) {
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
                .groupBy(book);
    }


    private BooleanExpression keyContain(BookSearchCondition bookSearchCondition) {
        return switch (bookSearchCondition.getKey()) {
            case ta ->
                    QBook.book.title.contains(bookSearchCondition.getQuery()).or(QBook.book.author.contains(bookSearchCondition.getQuery()));
            case t -> QBook.book.title.contains(bookSearchCondition.getQuery());
            case a -> QBook.book.author.contains(bookSearchCondition.getQuery());
        };

    }


}

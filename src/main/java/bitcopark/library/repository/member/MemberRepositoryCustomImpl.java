package bitcopark.library.repository.member;

import bitcopark.library.dto.QBookStatusDTO;
import bitcopark.library.entity.book.QBookBorrow;
import bitcopark.library.entity.book.QBookReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.QMember;
import bitcopark.library.dto.BookStatusDTO;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public BookStatusDTO findBookStatus(Member loginMember) {
        QMember member = QMember.member;
        QBookBorrow bookBorrow = QBookBorrow.bookBorrow;
        QBookReservation bookReservation = QBookReservation.bookReservation;
        return queryFactory.select(new QBookStatusDTO(
                                (JPAExpressions
                                        .select(bookBorrow.count())
                                        .from(bookBorrow)
                                        .where(bookBorrow.member.eq(loginMember), bookBorrow.returnDate.isNull())
                                ),
                                (JPAExpressions
                                        .select(bookReservation.count())
                                        .from(bookReservation)
                                        .where(bookReservation.member.eq(loginMember))
                                ),
                                (JPAExpressions
                                        .select(bookBorrow.count())
                                        .from(bookBorrow)
                                        .where(bookBorrow.returnDueDate.lt(LocalDate.now()), bookBorrow.returnDate.isNull())
                                )
                        )
                )
                .from(member)
                .where(member.email.eq(loginMember.getEmail()))
                .fetchOne();
    }

}

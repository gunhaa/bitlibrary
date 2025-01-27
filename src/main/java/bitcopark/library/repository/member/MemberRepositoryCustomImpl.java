package bitcopark.library.repository.member;

import bitcopark.library.entity.book.QBookBorrow;
import bitcopark.library.entity.book.QBookReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.QMember;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.book.BookStatusDTO;
import bitcopark.library.repository.book.QBookStatusDTO;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final MemberRepository memberRepository;

    @Override
    public BookStatusDTO findBookStatus(LoginMemberDTO loginMember) {
        QMember member = QMember.member;
        QBookBorrow bookBorrow = QBookBorrow.bookBorrow;
        QBookReservation bookReservation = QBookReservation.bookReservation;
        Member findMember = memberRepository.findByEmail(loginMember.getEmail()).orElseThrow(() -> new IllegalArgumentException("not valid email"));
        return queryFactory.select(new QBookStatusDTO(
                                (JPAExpressions
                                        .select(bookBorrow.count())
                                        .from(bookBorrow)
                                        .where(bookBorrow.member.eq(findMember), bookBorrow.returnDate.isNull())
                                ),
                                (JPAExpressions
                                        .select(bookReservation.count())
                                        .from(bookReservation)
                                        .where(bookReservation.member.eq(findMember))
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

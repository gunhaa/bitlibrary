package bitcopark.library.repository.Member;

import bitcopark.library.entity.book.QBook;
import bitcopark.library.entity.member.QBookRequest;
import bitcopark.library.entity.member.QMember;
import bitcopark.library.service.Member.BookRequestPageDto;
import bitcopark.library.service.Member.QBookRequestPageDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRequestRepositoryCustomImpl implements BookRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BookRequestPageDto> getBookRequestPage(Pageable pageable) {
        QBookRequest bookRequest = QBookRequest.bookRequest;
        QMember member = QMember.member;
        List<BookRequestPageDto> content = queryFactory.select(new QBookRequestPageDto(
                        bookRequest.isbn,
                        member.name,
                        bookRequest.bookTitle,
                        bookRequest.bookRequestApprove,
                        bookRequest.opinion,
                        bookRequest.createdDate
                ))
                .from(bookRequest)
                .leftJoin(bookRequest.member, member)
                .orderBy(bookRequest.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(bookRequest.count())
                .from(bookRequest);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}

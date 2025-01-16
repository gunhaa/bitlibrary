package bitcopark.library.repository.Member;

import bitcopark.library.entity.book.QBook;
import bitcopark.library.entity.member.QBookRequest;
import bitcopark.library.entity.member.QMember;
import bitcopark.library.service.Member.BookRequestPageDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRequestRepositoryCustomImpl implements BookRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookRequestPageDto> getBookRequestPage(Pageable page) {
        QBookRequest bookRequest = QBookRequest.bookRequest;
        QMember member = QMember.member;
        return null;
//                return queryFactory.select(new QBookRequestListDto(
//                        bookRequest.isbn,
//                member.name,
//
//

//        ));
    }

}

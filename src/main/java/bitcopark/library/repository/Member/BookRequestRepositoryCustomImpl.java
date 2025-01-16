package bitcopark.library.repository.Member;

import bitcopark.library.repository.Book.BookRepositoryCustom;
import bitcopark.library.service.Member.BookRequestListDto;
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
    public List<BookRequestListDto> getBookRequestPage(Pageable page) {

        return null;
    }

}

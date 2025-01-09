package bitcopark.library.repository.Book;

import bitcopark.library.controller.search.BookDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookDTO> findAllBooks() {
        return List.of();
    }


}

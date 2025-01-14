package bitcopark.library.service.Book;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.entity.Book.BookLike;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Book.BookLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookLikeService {

    private final BookLikeRepository bookFavoriteRepository;

    @Transactional
    public BookLike addBookLike(Book book, Member member){
        BookLike bookFavorite = BookLike.builder()
                .book(book)
                .member(member)
                .build();
        return bookFavoriteRepository.save(bookFavorite);
    }

}

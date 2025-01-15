package bitcopark.library.repository.Book;

import bitcopark.library.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> , BookRepositoryCustom {

    Optional<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

    @Query("select b from Book b")
    List<Book> selectAll();

}

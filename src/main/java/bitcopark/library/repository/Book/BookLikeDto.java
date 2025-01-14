package bitcopark.library.repository.Book;

import lombok.Data;

@Data
public class BookLikeDto {
    private String isbn;

    public BookLikeDto(String isbn) {
        this.isbn = isbn;
    }
}

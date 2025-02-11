package bitcopark.library.controller.book;

import lombok.Data;

@Data
public class BookDeleteDto {

    private String isbn;

    public BookDeleteDto(String isbn) {
        this.isbn = isbn;
    }
}

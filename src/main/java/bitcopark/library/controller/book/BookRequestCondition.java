package bitcopark.library.controller.book;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequestCondition {

    private String isbn;
    private String email;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private LocalDate bookPublicationDate;
    private String opinion;

}

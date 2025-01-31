package bitcopark.library.dto;

import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.entity.member.BookRequestApprove;
import lombok.Data;

@Data
public class BookApplicationResponse {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private BookRequestApprove approve;

    public BookApplicationResponse(BookRequest bookRequest) {
        this.id = bookRequest.getId();
        this.title = bookRequest.getBookTitle();
        this.author = bookRequest.getAuthor();
        this.publisher = bookRequest.getPublisher();
        this.approve = bookRequest.getBookRequestApprove();
    }
}

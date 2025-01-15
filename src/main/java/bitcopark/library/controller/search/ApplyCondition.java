package bitcopark.library.controller.search;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplyCondition {

    private String title;
    private Long memberId;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private LocalDate bookPublicationDate;
    private String opinion;

}

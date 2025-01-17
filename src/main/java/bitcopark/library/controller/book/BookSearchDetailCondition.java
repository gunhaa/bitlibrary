package bitcopark.library.controller.book;

import lombok.Data;

@Data
public class BookSearchDetailCondition {

    private String query;
    private String author;
    private String pub;
    private String startYear;
    private String endYear;
    private Long memberId;
    private Integer size;
}

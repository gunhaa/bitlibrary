package bitcopark.library.controller.search;

import lombok.Data;

import java.time.LocalDate;

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

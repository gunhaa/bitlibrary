package bitcopark.library.repository.book;

import lombok.Data;

@Data
public class BookSearchCondition {
    private String query;
    private SearchType key;
}

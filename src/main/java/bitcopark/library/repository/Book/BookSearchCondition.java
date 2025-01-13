package bitcopark.library.repository.Book;

import lombok.Data;

@Data
public class BookSearchCondition {
    private String query;
    private searchType key;
}

package bitcopark.library.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchBooklistAndLikelistDTO {
    private final List<BookSearchDto> bookList;
    private final List<BookLikeDto> likeList;
}

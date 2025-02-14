package bitcopark.library.dto;

import bitcopark.library.controller.search.LikeStatus;
import lombok.Data;

@Data
public class LikeCondition {

    private String email;
    private String isbn;
    private LikeStatus likeStatus;

}

package bitcopark.library.controller.search;

import lombok.Data;

@Data
public class LikeCondition {

    private String email;
    private String isbn;
    private LikeStatus likeStatus;

}

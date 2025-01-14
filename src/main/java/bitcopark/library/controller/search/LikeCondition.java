package bitcopark.library.controller.search;

import lombok.Data;

@Data
public class LikeCondition {

    private Long memberId;
    private String isbn;
    private LikeStatus likeStatus;

}

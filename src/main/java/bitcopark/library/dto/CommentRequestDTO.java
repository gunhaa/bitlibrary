package bitcopark.library.dto;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private String content;
    private Long boardId;
}

package bitcopark.library.dto;

import bitcopark.library.entity.board.Reply;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MyReplyResponse {

    private Long id;
    private String content;
    private LocalDate createdDate;

    public MyReplyResponse(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.createdDate = reply.getCreatedDate().toLocalDate();
    }
}

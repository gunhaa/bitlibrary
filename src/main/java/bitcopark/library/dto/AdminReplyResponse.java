package bitcopark.library.dto;

import bitcopark.library.entity.board.Reply;
import bitcopark.library.entity.board.ReplyDelFlag;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminReplyResponse {

    private Long id;
    private String content;
    private String name;
    private LocalDate createdDate;
    private boolean isDeleted;

    public AdminReplyResponse(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.name = reply.getMember().getName().split(" ")[2];
        this.createdDate = reply.getCreatedDate().toLocalDate();
        this.isDeleted = reply.getReplyDelFlag() == ReplyDelFlag.Y;
    }
}

package bitcopark.library.dto;

import bitcopark.library.entity.board.Board;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MyBoardResponse {

    private Long id;
    private String title;
    private LocalDate createdDate;

    public MyBoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.createdDate = board.getCreatedDate().toLocalDate();
    }
}

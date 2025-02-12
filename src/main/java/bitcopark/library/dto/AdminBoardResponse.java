package bitcopark.library.dto;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardDelFlag;
import bitcopark.library.entity.board.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminBoardResponse {

    private Long id;
    private String title;
    private String name;
    private LocalDate createdDate;
    private boolean isDeleted;

    private String link;

    public AdminBoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.name = board.getMember().getName().split(" ")[2];
        this.createdDate = board.getCreatedDate().toLocalDate();
        this.isDeleted = board.getBoardDelFlag() == BoardDelFlag.Y;
        this.link = generateLink(board.getCategory());
    }

    // 게시글 상세 주소 링크 생성
    private String generateLink(Category category) {
        StringBuilder linkBuilder = new StringBuilder();

        // 카테고리 정보로 링크 생성 (부모 카테고리가 없을 때까지 반복)
        Category currentCategory = category;
        while (currentCategory != null) {
            linkBuilder.insert(0, "/" + currentCategory.getCategoryEngName()); // 앞에 카테고리 추가
            currentCategory = currentCategory.getParentCategory(); // 부모 카테고리로 이동
        }

        // 게시글 ID 추가
        linkBuilder.append("/" + id);
        return linkBuilder.toString();
    }
}

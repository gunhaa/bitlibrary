package bitcopark.library.dto;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.board.SecretFlag;
import bitcopark.library.entity.member.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardRequestDTO {
    private String title;
    private String content;
    private SecretFlag secretFlag;
    private List<MultipartFile> images = new ArrayList<>();

    public Board toEntity(Member member, Category category){

        if( secretFlag == null ){
            secretFlag = SecretFlag.N;
        }

        return Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .secretFlag(secretFlag)
                .category(category)
                .build();
    }
}

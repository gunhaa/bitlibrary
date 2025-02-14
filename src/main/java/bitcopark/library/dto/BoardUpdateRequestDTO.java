package bitcopark.library.dto;

import bitcopark.library.entity.board.SecretFlag;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardUpdateRequestDTO {
    private String title;
    private String content;
    private SecretFlag secretFlag;
    private List<String> deleteImgList;
    private Long boardId;
}

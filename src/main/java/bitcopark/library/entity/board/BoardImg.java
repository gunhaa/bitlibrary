package bitcopark.library.entity.board;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BoardImg {

    @Id
    @GeneratedValue
    @Column(name = "boardImg_id")
    private Long id;

    private String originalImg;

    private String renameImg;

    private int orderImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void createRenameImg(){
        // need rename logic
        String extension = "";

        int dotIdx = builder().originalImg.lastIndexOf(".");

        if( dotIdx != -1 ) {
            extension = builder().originalImg.substring(dotIdx);
        }

        String newFileName = UUID.randomUUID().toString();

        renameImg = newFileName + extension;
    }
}

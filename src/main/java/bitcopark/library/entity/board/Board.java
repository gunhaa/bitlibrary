package bitcopark.library.entity.board;

import bitcopark.library.controller.user.UserController;
import bitcopark.library.dto.BoardUpdateRequestDTO;
import bitcopark.library.entity.clazz.ClassApplicant;
import bitcopark.library.entity.clazz.ClassSchedule;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static bitcopark.library.controller.user.UserController.IMG_UPLOAD_PATH;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;
    private String content;

    @Builder.Default
    private Long count = 0L;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BoardDelFlag boardDelFlag = BoardDelFlag.N;

    @Enumerated(EnumType.STRING)
    private SecretFlag secretFlag;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    @Builder.Default
    private List<Reply> replyList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "classSchedule_id")
    private ClassSchedule classSchedule;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    @Builder.Default
    private List<BoardImg> boardImgList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "anotherLibrary_id")
    private AnotherLibrary anotherLibrary;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    @Builder.Default
    private List<ClassApplicant> classApplicantList = new ArrayList<>();

    // 소프트 삭제라면 해당 자식 객체들도 전부 상태가 바뀌어야하는가?
    public BoardDelFlag changeBoardDelFlag(){
        this.boardDelFlag = this.boardDelFlag == BoardDelFlag.Y ? BoardDelFlag.N :  BoardDelFlag.Y;
        return this.boardDelFlag;
    }

    public void updatePost(BoardUpdateRequestDTO boardUpdateRequestDTO) {
        this.title = boardUpdateRequestDTO.getTitle();
        this.content = boardUpdateRequestDTO.getContent();


        for (int i = 0; i < boardUpdateRequestDTO.getDeleteImgList().size(); i++) {
            System.out.println(boardUpdateRequestDTO.getDeleteImgList().get(i));
        }

        for( String image : boardUpdateRequestDTO.getDeleteImgList()){
            boardImgList.remove(Integer.parseInt(image));
        }

        System.out.println(boardUpdateRequestDTO.getFiles().length);
        for( MultipartFile file : boardUpdateRequestDTO.getFiles() ){
            System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        }

        if( boardUpdateRequestDTO.getFiles() != null && boardUpdateRequestDTO.getFiles().length > 0 ){
            for( int i = 0; i < boardImgList.size(); i++ ) {
                for( MultipartFile file : boardUpdateRequestDTO.getFiles()){
                    if( ! file.getOriginalFilename().equals(boardImgList.get(i).getOriginalImg())){

                        BoardImg boardImg = BoardImg.builder()
                                .pathImg(IMG_UPLOAD_PATH)
                                .orderImg(i)
                                .originalImg(file.getOriginalFilename())
                                .board(this)
                                .build();

                        boardImg.createRenameImg();

                        boardImgList.set(i, boardImg);
                    }
                }
            }
        }
    }
}

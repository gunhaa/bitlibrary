package bitcopark.library.entity.board;

import bitcopark.library.dto.BoardUpdateRequestDTO;
import bitcopark.library.entity.clazz.ClassApplicant;
import bitcopark.library.entity.clazz.ClassSchedule;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        List<Integer> deleteList = new ArrayList<>();
        for( String image : boardUpdateRequestDTO.getDeleteImgList()){
            System.out.println(image);
            for( BoardImg prevImg : boardImgList ) {
                if( prevImg.getOrderImg() == Integer.parseInt(image)){
                    deleteList.add(Integer.parseInt(image));
                }
            }
        }

        for( Integer removeName : deleteList ) {
            for( BoardImg img : boardImgList ) {
                if( img.getOrderImg() == removeName ){
                    boardImgList.remove(img);
                }
            }
        }
    }
}

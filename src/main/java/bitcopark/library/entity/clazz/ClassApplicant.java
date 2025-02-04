package bitcopark.library.entity.clazz;
import bitcopark.library.entity.audit.BaseAuditEntity;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassApplicant extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "classApplicant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ApprovalType approvalType = ApprovalType.N;


}

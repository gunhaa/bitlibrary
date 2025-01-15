package bitcopark.library.entity.member;

import bitcopark.library.entity.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BookRequest extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "bookRequest_id")
    private Long id;
    private String author;
    private String publisher;
    private String title;

    @Enumerated(EnumType.STRING)
    private BookRequestApprove bookRequestApprove;

    private LocalDate publicationDate;
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}

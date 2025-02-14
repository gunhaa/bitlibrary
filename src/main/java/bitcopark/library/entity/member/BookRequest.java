package bitcopark.library.entity.member;

import bitcopark.library.dto.BookRequestCondition;
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

    @Column(unique = true)
    private String isbn;

    private String bookTitle;
    private String publisher;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookRequestApprove bookRequestApprove;

    private LocalDate publicationDate;
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void bookApprovalStatusChange(String approval){
        if(approval.equals("Y")){
            this.bookRequestApprove = BookRequestApprove.Y;
        } else if(approval.equals("N")){
            this.bookRequestApprove = BookRequestApprove.N;
        } else if(approval.equals("W")){
            this.bookRequestApprove = BookRequestApprove.W;
        }
    }

    public void bookRequestStatusUpdate(BookRequestCondition bookRequestCondition){
        this.isbn = bookRequestCondition.getIsbn();
        this.bookTitle = bookRequestCondition.getBookTitle();
        this.author = bookRequestCondition.getBookAuthor();
        this.publisher = bookRequestCondition.getBookPublisher();
        this.publicationDate = bookRequestCondition.getBookPublicationDate();
        this.opinion = bookRequestCondition.getOpinion();
    }
}

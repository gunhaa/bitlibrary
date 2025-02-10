package bitcopark.library.entity.member;

import bitcopark.library.entity.book.BookBorrow;
import bitcopark.library.entity.book.BookLike;
import bitcopark.library.entity.clazz.ClassApplicant;
import bitcopark.library.entity.librarySeatBooking.SeatReservation;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Reply;
import bitcopark.library.entity.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"email", "name", "authority"})
public class Member extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String name;

    private boolean isDeleted;
    private LocalDateTime deletedAt;

    private String authority;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    //    @Builder.Default
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    //    @Builder.Default
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    @Builder.Default
    private List<BookLike> bookFavoriteList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    @Builder.Default
    private List<ClassApplicant> classApplicantList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    @Builder.Default
    private List<BookBorrow> bookBorrowList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    @Builder.Default
    private List<SeatReservation> seatReservationList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<BookRequest> bookRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<BookLike> bookLikeList = new ArrayList<>();

    public static Member createOAuth2Member(String email, String name, String authority) {
        Member member = new Member();
        member.email = email;
        member.name = name;
        member.authority = authority;

        return member;
    }
}

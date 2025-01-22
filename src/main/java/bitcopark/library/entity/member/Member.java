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
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"email", "name", "phoneNumber", "gender", "birthDate", "address", "delFlag", "authority"})
public class Member extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    @Enumerated(STRING)
    private MemberGender gender;

    private LocalDate birthDate;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private MemberDelFlag delFlag;

    @Enumerated(STRING)
    private MemberAuthority authority;

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

    // member/admin만 파라미터로 받아서 하나의 메서드로 통합
    public static Member createMember(String email, String password, String name, String phoneNumber, MemberGender gender, LocalDate birthDate, Address address) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.name = name;
        member.phoneNumber = phoneNumber;
        member.gender = gender;
        member.birthDate = birthDate;
        member.address = address;
        member.delFlag = MemberDelFlag.N;
        member.authority = MemberAuthority.MEMBER;

        return member;
    }

    public static Member createAdmin(String email, String password, String name, String phoneNumber, MemberGender gender, LocalDate birthDate, Address address) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.name = name;
        member.phoneNumber = phoneNumber;
        member.gender = gender;
        member.birthDate = birthDate;
        member.address = address;
        member.delFlag = MemberDelFlag.N;
        member.authority = MemberAuthority.ADMIN;

        return member;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

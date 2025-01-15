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

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"email", "name", "phoneNumber", "gender", "birth", "address", "delFlag", "authority"})
public class Member extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;

    private MemberGender gender;

    private int birth;

    @Embedded
    private Address address;

    private MemberDelFlag delFlag;
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

    // member/admin만 파라미터로 받아서 하나의 메서드로 통합
    public static Member createMember(String email, String name, String phoneNumber, MemberGender gender, int birth, Address address) {
        Member member = new Member();
        member.email = email;
        member.name = name;
        member.phoneNumber = phoneNumber;
        member.gender = gender;
        member.birth = birth;
        member.address = address;
        member.delFlag = MemberDelFlag.N;
        member.authority = MemberAuthority.MEMBER;

        return member;
    }

    public static Member createAdmin(String email, String name, String phoneNumber, MemberGender gender, int birth, Address address) {
        Member member = new Member();
        member.email = email;
        member.name = name;
        member.phoneNumber = phoneNumber;
        member.gender = gender;
        member.birth = birth;
        member.address = address;
        member.delFlag = MemberDelFlag.N;
        member.authority = MemberAuthority.ADMIN;

        return member;
    }


}

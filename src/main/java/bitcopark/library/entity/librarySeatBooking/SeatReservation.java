package bitcopark.library.entity.librarySeatBooking;

import bitcopark.library.entity.audit.CreatedAuditEntity;
import bitcopark.library.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SeatReservation extends CreatedAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "seatReservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int seatNo;

    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
}

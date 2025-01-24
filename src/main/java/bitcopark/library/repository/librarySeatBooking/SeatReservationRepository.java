package bitcopark.library.repository.librarySeatBooking;

import bitcopark.library.entity.librarySeatBooking.SeatReservation;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatReservationRepository extends JpaRepository<SeatReservation , Long> {

    List<SeatReservation> findByMember(Member member);

    Optional<SeatReservation> findByIdAndMember(Long id, Member member);
}

package bitcopark.library.service.LibrarySeatBooking;

import bitcopark.library.entity.librarySeatBooking.SeatReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.librarySeatBooking.SeatReservationRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatReservationService {

    private final SeatReservationRepository seatReservationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SeatReservation registerSeatReservation(Member member, int seatNo){
        SeatReservation seatReservation = SeatReservation.builder()
                .member(member)
                .seatNo(seatNo)
                .build();
        return seatReservationRepository.save(seatReservation);
    }

    @Transactional
    public void delete(Long id, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        SeatReservation seatReservation = seatReservationRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new IllegalArgumentException("not found seatReservation: " + id));

        seatReservationRepository.delete(seatReservation);
    }
}

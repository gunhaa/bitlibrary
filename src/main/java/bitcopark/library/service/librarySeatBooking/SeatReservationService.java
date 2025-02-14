package bitcopark.library.service.librarySeatBooking;

import bitcopark.library.dto.RoomReservationResponse;
import bitcopark.library.dto.SeatReservationResponse;
import bitcopark.library.entity.librarySeatBooking.SeatReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.librarySeatBooking.SeatReservationRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<SeatReservationResponse> getSeatReservations(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        return seatReservationRepository.findByMember(member)
                .stream().map(SeatReservationResponse::new).toList();
    }

    public List<RoomReservationResponse> getRoomReservations(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        return seatReservationRepository.findByMember(member)
                .stream().map(RoomReservationResponse::new).toList();
    }
}

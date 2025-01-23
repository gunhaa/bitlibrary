package bitcopark.library.repository.board;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardDelFlag;
import bitcopark.library.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<List<Board>> findByMember(Member member);

    Optional<List<Board>> findByMemberAndBoardDelFlag(Member member, BoardDelFlag boardDelFlag);

    Optional<Board> findByMemberAndId(Member member, Long boardId);

    Page<Board> findByCategoryId(Long id, Pageable pageable);
}

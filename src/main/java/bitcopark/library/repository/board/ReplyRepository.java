package bitcopark.library.repository.board;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Reply;
import bitcopark.library.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<List<Reply>> findByBoard(Board board);

    Page<Reply> findByMember(Member member, Pageable pageable);

    List<Reply> findByIdIn(List<Long> ids);

    Optional<Reply> findByMemberAndId(Member member, Long id);
}

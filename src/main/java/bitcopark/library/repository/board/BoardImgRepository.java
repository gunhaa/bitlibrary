package bitcopark.library.repository.board;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg , Long>{

    List<BoardImg> findByBoard(Board board);

}

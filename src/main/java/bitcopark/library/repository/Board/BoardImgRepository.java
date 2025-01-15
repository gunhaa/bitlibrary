package bitcopark.library.repository.Board;

import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg , Long>{

    List<BoardImg> findByBoard(Board board);

}

package bitcopark.library.service.Board;

import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.dto.MyBoardResponse;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardDelFlag;
import bitcopark.library.entity.board.BoardImg;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.member.Member;
import bitcopark.library.exception.BoardNotFoundException;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.board.BoardImgRepository;
import bitcopark.library.repository.board.BoardRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardImgRepository boardImgRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Board writePost(LoginMemberDTO memberDTO, BoardRequestDTO boardRequestDTO, Category category){
        Long id = memberRepository.findMemberIdByName(memberDTO.getEmail());

        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found member" + id));

        return boardRepository.save(boardRequestDTO.toEntity(member,category));
    }

    @Transactional
    public void updatePost(LoginMemberDTO memberDTO, BoardRequestDTO boardRequestDTO, Category category) {

    }

    public Optional<Board> selectBoard(Long id) {
        return boardRepository.findById(id);
    }

    // AOP같은 로직을 통한 인증 필요
    @Transactional
    public BoardDelFlag deletePost(LoginMemberDTO memberDTO, Board board){
        Long id = memberRepository.findMemberIdByName(memberDTO.getEmail());

        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found member" + id));

        Board findBoard = boardRepository.findByMemberAndId(member, board.getId())
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다."));

        return findBoard.changeBoardDelFlag();

    }

    public Page<Board> selectBoardList(Long id, Pageable pageable) {
        return boardRepository.findByCategoryId(id, pageable);
    }

    @Transactional
    public BoardImg insertBoardImg(Board board, String originalName, int orderImg) {
        BoardImg boardImg = BoardImg.builder()
                .originalImg(originalName)
                .board(board)
                .orderImg(orderImg)
                .build();

        boardImg.createRenameImg();
        boardImgRepository.save(boardImg);

        return boardImg;
    }


    public Page<MyBoardResponse> getMyBoards(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member" + email));

        Page<Board> boardPage = boardRepository.findByMember(member, pageable);

        List<MyBoardResponse> dtoList = boardPage.getContent().stream().map(MyBoardResponse::new).toList();

        return new PageImpl<>(dtoList, pageable, boardPage.getTotalElements());
    }
}

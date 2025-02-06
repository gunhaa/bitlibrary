package bitcopark.library.service.Board;

import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.dto.LoginResponseDTO;
import bitcopark.library.entity.board.*;
import bitcopark.library.entity.member.Member;
import bitcopark.library.exception.BoardNotFoundException;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.board.BoardImgRepository;
import bitcopark.library.repository.board.BoardRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // AOP같은 로직을 통한 인증 필요
    @Transactional
    public BoardDelFlag deletePost(Member member, Board board){
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


}

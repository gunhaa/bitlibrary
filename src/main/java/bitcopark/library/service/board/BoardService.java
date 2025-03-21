package bitcopark.library.service.board;

import bitcopark.library.dto.*;
import bitcopark.library.entity.board.*;
import bitcopark.library.entity.member.Member;
import bitcopark.library.exception.BoardNotFoundException;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.repository.board.BoardImgRepository;
import bitcopark.library.repository.board.BoardRepository;
import bitcopark.library.repository.board.ReplyRepository;
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
    private final ReplyRepository replyRepository;

    @Transactional
    public Board writePost(LoginMemberDTO memberDTO, BoardRequestDTO boardRequestDTO, Category category){
        Long id = memberRepository.findMemberIdByName(memberDTO.getEmail());

        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found member" + id));

        return boardRepository.save(boardRequestDTO.toEntity(member,category));
    }

    @Transactional
    public Board updatePost(LoginMemberDTO loginMemberDTO, BoardUpdateRequestDTO boardUpdateRequestDTO) {
        Member member = memberRepository.findByEmail(loginMemberDTO.getEmail()).get();

        Board board = boardRepository.findByMemberAndId(member, boardUpdateRequestDTO.getBoardId()).get();

        board.updatePost(boardUpdateRequestDTO);

        return boardRepository.save(board);
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
        return boardRepository.findByCategoryIdAndBoardDelFlag(id, pageable, BoardDelFlag.N);
    }

    @Transactional
    public BoardImg insertBoardImg(Board board, String originalName, String pathImg, int orderImg) {
        BoardImg boardImg = BoardImg.builder()
                .originalImg(originalName)
                .pathImg(pathImg)
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

        Page<Board> boardPage = boardRepository.findByMemberAndBoardDelFlag(member, BoardDelFlag.N, pageable);

        List<MyBoardResponse> dtoList = boardPage.getContent().stream().map(MyBoardResponse::new).toList();

        return new PageImpl<>(dtoList, pageable, boardPage.getTotalElements());
    }

    public Page<AdminBoardResponse> getAllBoards(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        List<AdminBoardResponse> dtoList = boardPage.getContent().stream().map(AdminBoardResponse::new).toList();

        return new PageImpl<>(dtoList, pageable, boardPage.getTotalElements());
    }

    @Transactional
    public void toggleDeletionStatus(List<Long> ids) {
        List<Board> boards = boardRepository.findByIdIn(ids);
        boards.forEach(Board::changeBoardDelFlag);
    }
        

    @Transactional
    public Reply writeComment(LoginMemberDTO loginMemberDTO, CommentRequestDTO commentRequestDTO){

        Member member = memberRepository.findByEmail(loginMemberDTO.getEmail()).get();

        Board board = boardRepository.findByMemberAndId(member, commentRequestDTO.getBoardId()).get();

        Reply reply = Reply.builder()
                .member(member)
                .content(commentRequestDTO.getContent())
                .board(board)
                .build();

        return replyRepository.save(reply);
    }

    @Transactional
    public ReplyDelFlag deleteComment(LoginMemberDTO loginMemberDTO, Long commentId) {
        Member member = memberRepository.findByEmail(loginMemberDTO.getEmail()).get();

        Reply reply = replyRepository.findByMemberAndId(member, commentId).get();

        return reply.changeDelFlag();
    }

    public List<Reply> selectReplyList(Board board) {
        return replyRepository.findByBoardAndReplyDelFlag(board, ReplyDelFlag.N);
    }
}

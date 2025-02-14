package bitcopark.library.service.mainPageTest;

import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.member.Member;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.repository.board.BoardRepository;
import bitcopark.library.service.board.BoardService;
import bitcopark.library.service.board.CategoryService;
import bitcopark.library.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class WritePostTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("게시글_작성")
    public void 게시글_작성() {

        //given
        Category 이용자_마당 = categoryService.createNewCategory("이용자 마당", "user");
        Category 공지사항 = categoryService.createNewCategoryWithParentCategory("공지사항", 이용자_마당, "notice");
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, "ROLE_ADMIN");
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, "ROLE_ADMIN");
        BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
        boardRequestDTO.setTitle("제목");
        boardRequestDTO.setContent("내용");
        boardService.writePost(loginMemberDTO, boardRequestDTO, 공지사항);

        //when
        List<Board> boardList = boardRepository.findByMember(OAuthNaverGunha).get();
        Board findBoard = boardRepository.findByMember(OAuthNaverGunha).get().get(0);

        //then
        Assertions.assertThat(boardList.size()).isEqualTo(1);
        Assertions.assertThat(boardList.get(0)).isEqualTo(findBoard);
    }

    @Test
    public void 게시글_없는아이디로_작성() {
        //given
        Category 이용자_마당 = categoryService.createNewCategory("이용자 마당", "user");
        Category 공지사항 = categoryService.createNewCategoryWithParentCategory("공지사항", 이용자_마당, "notice");
        String naverEmail1 = "wh82991@naver.com";
        String naverName1 = "naver1 YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        Member OAuthNaverGunha1 = memberService.joinOAuth2Member(naverEmail1, naverName1, "ROLE_ADMIN");

        String naverEmail2 = "wh82992@naver.com";
        String naverName2 = "naver2 YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        Member OAuthNaverGunha2 = memberService.joinOAuth2Member(naverEmail2, naverName2, "ROLE_ADMIN");

        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail1, naverName1, "ROLE_ADMIN");
        BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
        boardRequestDTO.setTitle("제목");
        boardRequestDTO.setContent("내용");

        //when
        Board board = boardService.writePost(loginMemberDTO, boardRequestDTO, 공지사항);
        Board findBoard = boardRepository.findByMember(OAuthNaverGunha1).get().get(0);

        //then
        Assertions.assertThat(board).isEqualTo(findBoard);
        
        //when,then
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            String naverEmail3 = "난 회원가입 안한 사람 이메일이야";
            String naverName3 = "난 회원가입 안한 사람 이름이야";

            LoginMemberDTO loginMemberDTO3 = new LoginMemberDTO(naverEmail3, naverName3, "ROLE_ADMIN");
            BoardRequestDTO boardRequestDTO3 = new BoardRequestDTO();
            boardRequestDTO3.setTitle("제목");
            boardRequestDTO3.setContent("내용");

            //when, then
            boardService.writePost(loginMemberDTO3, boardRequestDTO3, 공지사항);
        });
    }
}

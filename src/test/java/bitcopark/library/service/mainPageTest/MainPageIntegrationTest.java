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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class MainPageIntegrationTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CategoryService categoryService;

    @DisplayName("공지사항 조회")
    @Test
    public void 통합테스트_공지사항_조회(){

        // given: 테스트 준비 단계
        Category 이용자_마당 = categoryService.createNewCategory("이용자 마당", "user");
        Category 공지사항 = categoryService.createNewCategoryWithParentCategory("공지사항", 이용자_마당, "notice");

        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, "ROLE_ADMIN");

        // given
        for (int i = 0; i < 10; i++) {
            LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, "ROLE_ADMIN");
            BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
            boardRequestDTO.setTitle("제목" + i);
            boardRequestDTO.setContent("내용" + i);
            boardService.writePost(loginMemberDTO, boardRequestDTO, 공지사항);
        }

        // when
        Pageable limit = PageRequest.of(0, 5);
        List<Board> boardList = boardRepository.selectNoticeLimit(limit);

        // then
        Assertions.assertThat(boardList.size()).isEqualTo(5);
    }

}

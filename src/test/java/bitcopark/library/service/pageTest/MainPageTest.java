package bitcopark.library.service.pageTest;

import bitcopark.library.config.SecurityConfig;
import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.JwtUtil;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.board.BoardRepository;
import bitcopark.library.service.Board.BoardService;
import bitcopark.library.service.Board.CategoryService;
import bitcopark.library.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class MainPageTest {

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
    public void 공지사항_조회(){

        // Given: 테스트 준비 단계
        Category 이용자_마당 = categoryService.createNewCategory("이용자 마당", "user");
        Category 공지사항 = categoryService.createNewCategoryWithParentCategory("공지사항", 이용자_마당, "notice");

        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, "ROLE_ADMIN");

        // Given: 게시글 10개 작성 (공지사항 카테고리로)
        for (int i = 0; i < 10; i++) {
            LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, "ROLE_ADMIN");
            BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
            boardRequestDTO.setTitle("제목" + i);
            boardRequestDTO.setContent("내용" + i);
            boardService.writePost(loginMemberDTO, boardRequestDTO, 공지사항);
        }

        // When: 공지사항 조회 (첫 페이지, 최대 10개)
        Pageable limit = PageRequest.of(0, 10);
        List<Board> boardList = boardRepository.selectNoticeLimit(limit);

        // Then: 조회된 공지사항 개수가 10개인지 확인
        Assertions.assertThat(boardList.size()).isEqualTo(10);
    }

}

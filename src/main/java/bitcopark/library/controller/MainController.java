package bitcopark.library.controller;

import bitcopark.library.entity.board.Board;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.board.BoardRepository;
import bitcopark.library.repository.book.BookStatusDTO;
import bitcopark.library.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String mainPage(@RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMember, Model model){
        model.addAttribute("loginMember", loginMember);
        if(loginMember != null){
            // 테스트 필요
            BookStatusDTO bookStatus = memberService.getBookStatus(loginMember);
            model.addAttribute("bookStatus", bookStatus);
        }

        Pageable limit = PageRequest.of(0, 5);
        List<Board> boardList = boardRepository.selectNoticeRecent5(limit);
        for (Board board : boardList) {
            System.out.println("board.getTitle() = " + board.getTitle());
        }
        model.addAttribute("boardList", boardList);

        return "common/main";
    }

}

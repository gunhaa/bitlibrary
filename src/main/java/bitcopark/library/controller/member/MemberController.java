package bitcopark.library.controller.member;

import bitcopark.library.oauth2.CustomOAuth2User;
import bitcopark.library.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        RedirectAttributes redirectAttributes) {

        if (error != null && error.equals("deleted")) {
            redirectAttributes.addFlashAttribute("message", "탈퇴된 회원입니다.\n" +
                    "도움이 필요하시면 help@email.com으로 문의 바랍니다.");
            return "redirect:/";
        }

        return "member/login";
    }

    @PostMapping("/secession")
    public String softDeleteMember(@AuthenticationPrincipal CustomOAuth2User user) {

        memberService.softDeleteMember(user.getName());

        return "redirect:/OAuth2/logout";
    }
}

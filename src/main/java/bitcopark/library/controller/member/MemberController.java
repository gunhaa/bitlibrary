package bitcopark.library.controller.member;

import bitcopark.library.dto.LoginRequestDTO;
import bitcopark.library.dto.LoginResponseDTO;
import bitcopark.library.entity.member.MemberDelFlag;
import bitcopark.library.service.Member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/member")
@SessionAttributes("loginMember")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String saveId = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "saveId".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst())
                .orElse(null);

        model.addAttribute("saveId", saveId);
        return "member/login";
    }

//    @PostMapping("/login")
//    public String login(LoginRequestDTO inputMember,
//                        Model model,
//                        @RequestHeader(value = "referer") String referer,
//                        @RequestParam(value = "saveId", required = false) String saveId,
//                        HttpServletResponse resp,
//                        RedirectAttributes ra) {
//
//        Optional<LoginResponseDTO> loginResponse = memberService.login(inputMember);
//
//        if (loginResponse.isEmpty()) {
//            ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
//            return "redirect:" + referer;
//        }
//
//        LoginResponseDTO loginMember = loginResponse.get();
//
//        if (loginMember.getDelFlag() == MemberDelFlag.Y) {
//            ra.addFlashAttribute("message", "탈퇴된 회원입니다");
//            return "redirect:" + referer;
//        }
//
//        model.addAttribute("loginMember", loginMember);
//
//        setLoginCookie(resp, loginMember.getEmail(), saveId);
//
//        ra.addFlashAttribute("message", "로그인 성공!!");
//        return "redirect:/";
//    }
//
//    private void setLoginCookie(HttpServletResponse resp, String memberEmail, String saveId) {
//        Cookie cookie = new Cookie("saveId", memberEmail);
//
//        if (saveId != null) {
//            cookie.setMaxAge(60 * 60 * 24 * 30);
//        } else {
//            cookie.setMaxAge(0);
//        }
//
//        cookie.setPath("/");
//        resp.addCookie(cookie);
//    }
}

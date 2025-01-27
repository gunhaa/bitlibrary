package bitcopark.library.controller;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.service.Member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String mainPage(@RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMember, Model model){
        model.addAttribute("loginMember", loginMember);
        if(loginMember != null){
            memberService.getBookStatus(loginMember);
        }
        return "common/main";
    }

}

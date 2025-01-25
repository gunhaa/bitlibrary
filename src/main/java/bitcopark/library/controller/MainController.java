package bitcopark.library.controller;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.jwt.LoginMemberDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@Slf4j
public class MainController {

    @GetMapping("/")
    public String mainPage(@RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMemberDTO, Model model){
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) - 1) % 7;
        model.addAttribute("dayOfWeek", dayOfWeek);
        model.addAttribute("loginMember", loginMemberDTO);

        return "common/main";
    }

}

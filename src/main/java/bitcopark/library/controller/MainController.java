package bitcopark.library.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;

@Controller
@Slf4j
public class MainController {

    @RequestMapping("/")
    public String mainPage(Model model){
        log.info("main controller used");
        // 오늘 날짜에서 요일 가져오기 (0: 일요일, 1: 월요일, ..., 6: 토요일)
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) - 1) % 7;

        model.addAttribute("dayOfWeek", dayOfWeek);
        return "common/main";
    }

}

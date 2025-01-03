package bitcopark.library.controller.intro;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IntroController {

    @GetMapping("/intro/introduce")
    public String intro(Model model){
        model.addAttribute("catLevel2", 7);
        model.addAttribute("catLevel3", 24);
        return "/intro/lib_greeting";
    }

}

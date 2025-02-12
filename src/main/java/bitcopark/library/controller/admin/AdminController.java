package bitcopark.library.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/members")
    public String showMemberListPage() {
        return "admin/member.html";
    }

    @GetMapping("/boards")
    public String showBoardListPage() {
        return "admin/board.html";
    }

    @GetMapping("/replies")
    public String showReplyListPage() {
        return "admin/reply.html";
    }
}

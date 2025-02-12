package bitcopark.library.controller.admin;

import bitcopark.library.dto.AdminMemberResponse;
import bitcopark.library.oauth2.CustomOAuth2User;
import bitcopark.library.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final MemberService memberService;

    @GetMapping("/members")
    public Page<AdminMemberResponse> getAllMembersExcludingCurrent(@AuthenticationPrincipal CustomOAuth2User user,
                                                                   Pageable pageable) {
        return memberService.getAllMembersExcludingCurrent(user.getName(), pageable);
    }
}

package bitcopark.library.controller.admin;

import bitcopark.library.dto.AdminBoardResponse;
import bitcopark.library.dto.AdminMemberResponse;
import bitcopark.library.dto.AdminReplyResponse;
import bitcopark.library.oauth2.CustomOAuth2User;
import bitcopark.library.service.board.BoardService;
import bitcopark.library.service.board.ReplyService;
import bitcopark.library.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/members")
    public Page<AdminMemberResponse> getAllMembersExcludingCurrent(@AuthenticationPrincipal CustomOAuth2User user,
                                                                   Pageable pageable) {
        return memberService.getAllMembersExcludingCurrent(user.getName(), pageable);
    }

    @PatchMapping("/member")
    public ResponseEntity<Void> toggleMembersDeletionStatus(@RequestBody List<String> emails) {
        memberService.toggleDeletionStatus(emails);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/boards")
    public Page<AdminBoardResponse> getAllBoards(Pageable pageable) {
        return boardService.getAllBoards(pageable);
    }

    @PatchMapping("/board")
    public ResponseEntity<Void> toggleBoardsDeletionStatus(@RequestBody List<Long> ids) {
        boardService.toggleDeletionStatus(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/replies")
    public Page<AdminReplyResponse> getAllReplies(Pageable pageable) {
        return replyService.getAllReplies(pageable);
    }

    @PatchMapping("/reply")
    public ResponseEntity<Void> toggleRepliesDeletionStatus(@RequestBody List<Long> ids) {
        replyService.toggleDeletionStatus(ids);
        return ResponseEntity.noContent().build();
    }
}

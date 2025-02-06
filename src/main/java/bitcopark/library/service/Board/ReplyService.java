package bitcopark.library.service.Board;

import bitcopark.library.dto.MyReplyResponse;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Reply;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.board.ReplyRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Reply addReply(String content, Member member, Board board){

        Reply reply = Reply.builder()
                .content(content)
                .member(member)
                .board(board)
                .build();
        replyRepository.save(reply);

        return reply;
    }

    public Page<MyReplyResponse> getMyReplies(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        Page<Reply> replyPage = replyRepository.findByMember(member, pageable);

        List<MyReplyResponse> dtoList = replyPage.getContent().stream().map(MyReplyResponse::new).toList();

        return new PageImpl<>(dtoList, pageable, replyPage.getTotalElements());
    }
}

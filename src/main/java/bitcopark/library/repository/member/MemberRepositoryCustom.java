package bitcopark.library.repository.member;

import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.book.BookStatusDTO;

public interface MemberRepositoryCustom {

    BookStatusDTO findBookStatus(Member loginMember);

}

package bitcopark.library.repository.member;

import bitcopark.library.entity.member.Member;
import bitcopark.library.dto.BookStatusDTO;

public interface MemberRepositoryCustom {

    BookStatusDTO findBookStatus(Member loginMember);

}

package bitcopark.library.repository.member;

import bitcopark.library.repository.book.BookStatusDTO;

public interface MemberRepositoryCustom {

    BookStatusDTO findBookStatus(String name);

}

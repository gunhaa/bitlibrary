package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class MemberInfoStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return switch (categoryLevel3.getCategoryName()) {
            case "내 글 관리" -> "study/member-info/board.html";
            case "회원탈퇴" -> "study/member-info/secession.html";
            default -> "common/main";
        };
    }
}

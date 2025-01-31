package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class MemberInfoStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return switch (categoryLevel3.getCategoryName()) {
            case "내 정보" -> "study/member-info/my-info.html";
            case "내 글 관리" -> "study/member-info/board.html";
            case "비밀번호 변경" -> "study/member-info/change-password.html";
            case "회원탈퇴" -> "study/member-info/secession.html";
            default -> "common/main";
        };
    }
}

package bitcopark.library.dto;

import bitcopark.library.entity.member.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminMemberResponse {

    private String email;
    private String name;
    private LocalDate createdDate;
    private boolean isDeleted;

    public AdminMemberResponse(Member member) {
        this.email = member.getEmail();
        this.name = member.getName().split(" ")[2];
        this.createdDate = member.getCreatedDate().toLocalDate();
        this.isDeleted = member.isDeleted();
    }
}

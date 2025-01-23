package bitcopark.library.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDto {

    private String email;
    private String role;

    public MemberDto(String email, String role) {
        this.email = email;
        this.role = role;
    }
}

package bitcopark.library.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDto {

    private String email;
    private String role;
    private String username;

    public MemberDto(String email, String username,String role) {
        this.email = email;
        this.username = username;
        this.role = role;
    }
}

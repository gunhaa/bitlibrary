package bitcopark.library.dto;

import lombok.Getter;

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

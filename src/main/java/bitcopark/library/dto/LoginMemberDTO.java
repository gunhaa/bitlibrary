package bitcopark.library.dto;

import lombok.Data;

@Data
public class LoginMemberDTO {

    private String name;
    private String role;
    private String email;

    public LoginMemberDTO(String email, String name, String role) {
        this.name = name;
        this.role = role;
        this.email = email;
    }
}

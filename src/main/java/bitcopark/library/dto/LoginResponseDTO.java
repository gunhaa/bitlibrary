package bitcopark.library.dto;

import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberAuthority;
import bitcopark.library.entity.member.MemberDelFlag;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginResponseDTO {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate birthDate;
    private Address address;
    private MemberAuthority authority;
    private MemberDelFlag delFlag;

    public LoginResponseDTO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.birthDate = member.getBirthDate();
        this.address = member.getAddress();
//        this.authority = member.getAuthority();
        this.delFlag = member.getDelFlag();
    }
}

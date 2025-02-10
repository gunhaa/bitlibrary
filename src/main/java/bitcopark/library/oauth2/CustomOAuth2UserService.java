package bitcopark.library.oauth2;

import bitcopark.library.dto.GoogleResponse;
import bitcopark.library.dto.NaverResponse;
import bitcopark.library.dto.OAuth2Response;
import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.MemberDto;
import bitcopark.library.repository.member.MemberRepository;
import bitcopark.library.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Profile("local")
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }else if (registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else {
            return null;
        }

        String name = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId()+" "+oAuth2Response.getName();
        String email = oAuth2Response.getEmail();

        Optional<Member> findMember = memberRepository.findByName(name);

        if(findMember.isEmpty()){
            memberService.joinOAuth2Member(email, name, "ROLE_USER");
            return new CustomOAuth2User(new MemberDto(email, name, "ROLE_USER"));
        }

        return new CustomOAuth2User(new MemberDto(email,name,findMember.get().getAuthority()));
    }
}

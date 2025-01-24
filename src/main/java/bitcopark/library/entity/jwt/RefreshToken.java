package bitcopark.library.entity.jwt;

import bitcopark.library.entity.audit.CreatedAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends CreatedAuditEntity {

    @GeneratedValue
    @Id
    private Long id;

    private String username;
    private String email;

    @Column(length = 400)
    private String refreshToken;
    private String expiration;

    public static RefreshToken createRefreshToken(String username, String email, String refreshToken, String expiration){
        RefreshToken refreshTokenInstance = new RefreshToken();
        refreshTokenInstance.username = username;
        refreshTokenInstance.email = email;
        refreshTokenInstance.refreshToken = refreshToken;
        refreshTokenInstance.expiration = expiration;
        return refreshTokenInstance;
    }

}

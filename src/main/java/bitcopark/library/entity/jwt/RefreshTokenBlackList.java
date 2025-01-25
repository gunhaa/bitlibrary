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
public class RefreshTokenBlackList extends CreatedAuditEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 300)
    private String refreshToken;

    public static RefreshTokenBlackList createRefreshTokenBlackList(String refreshToken){
        RefreshTokenBlackList refreshTokenBlackList = new RefreshTokenBlackList();
        refreshTokenBlackList.refreshToken = refreshToken;
        return refreshTokenBlackList;
    }
}

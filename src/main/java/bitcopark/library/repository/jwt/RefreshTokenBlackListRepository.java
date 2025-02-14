package bitcopark.library.repository.jwt;

import bitcopark.library.entity.jwt.RefreshTokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {

    Boolean existsByRefreshToken(String refreshToken);

}

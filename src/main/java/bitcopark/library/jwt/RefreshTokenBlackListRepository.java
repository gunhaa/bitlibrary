package bitcopark.library.jwt;

import bitcopark.library.entity.jwt.RefreshTokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {

    Boolean existsByRefreshToken(String refreshToken);

}

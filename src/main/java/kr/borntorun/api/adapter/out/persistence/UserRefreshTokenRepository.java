package kr.borntorun.api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.UserRefreshTokenEntity;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity, Long> {

	Optional<UserRefreshTokenEntity> findByUserId(long userId);

	UserRefreshTokenEntity findByUserIdAndRefreshToken(Long userId, String refreshToken);
}

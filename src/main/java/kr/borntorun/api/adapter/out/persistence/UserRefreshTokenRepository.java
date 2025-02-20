package kr.borntorun.api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.borntorun.api.domain.entity.UserRefreshTokenEntity;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity, Long> {

	Optional<UserRefreshTokenEntity> findByUserId(long userId);
}

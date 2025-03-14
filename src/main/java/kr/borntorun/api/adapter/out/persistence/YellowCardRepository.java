package kr.borntorun.api.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.YellowCardEntity;
import kr.borntorun.api.domain.entity.YellowCardMultiKey;

public interface YellowCardRepository extends JpaRepository<YellowCardEntity, YellowCardMultiKey> {

	boolean existsBySourceUserIdAndTargetUserId(long sourceUserId, long targetUserId);
}

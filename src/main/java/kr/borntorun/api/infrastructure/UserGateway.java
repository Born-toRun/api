package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.UserRepository;
import kr.borntorun.api.domain.constant.RoleType;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.infrastructure.model.CreateGuestQuery;
import kr.borntorun.api.infrastructure.model.ModifyUserQuery;
import kr.borntorun.api.infrastructure.model.SignUpUserQuery;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserGateway {

	private final UserRepository userRepository;

	public UserEntity searchBySocialId(String socialId) {
		return userRepository.findBySocialId(socialId)
		  .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
	}

	public UserEntity searchById(long userId) {
		return userRepository.findById(userId)
		  .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
	}

	public UserEntity modify(ModifyUserQuery query) {
		final UserEntity userEntity = searchById(query.userId());

		userEntity.modify(query.instagramId(), query.profileImageId());
		return userRepository.save(userEntity);
	}

	public String modify(SignUpUserQuery query) {
		final UserEntity userEntity = searchById(query.userId());

		userEntity.modify(query.userName(), query.crewId(), query.instagramId());
		return userRepository.save(userEntity).getName();
	}

	public List<UserEntity> searchByUserName(String userName) {
		return userRepository.findAllByNameContaining(userName);
	}

	public void remove(long userId) {
		userRepository.deleteById(userId);
	}

	public UserEntity create(CreateGuestQuery query) {
		UserEntity userEntity = UserEntity.builder()
		  .socialId(query.socialId())
		  .providerType(query.providerType())
		  .roleType(RoleType.GUEST)
		  .build();
		return userRepository.save(userEntity);
	}
}

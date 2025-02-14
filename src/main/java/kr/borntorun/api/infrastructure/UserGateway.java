package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.UserRepository;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.infrastructure.model.ModifyUserQuery;
import kr.borntorun.api.infrastructure.model.SignUpUserQuery;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserGateway {

  private final UserRepository userRepository;

  public UserEntity search(int userId) {
    return userRepository.findByIdAndIsDeletedFalse(userId)
        .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
  }

  public String modify(ModifyUserQuery query) {
    final UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(query.userId())
        .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

    userEntity.modify(query.instagramId(), query.profileImageId());
    return userRepository.save(userEntity).getName();
  }

  public String modify(SignUpUserQuery query) {
    final UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(query.userId())
        .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

    userEntity.modify(query.userName(), query.crewId(), query.instagramId());
    return userRepository.save(userEntity).getName();
  }

  public List<UserEntity> searchByUserName(String userName) {
    return userRepository.findAllByNameContainingAndIsDeletedFalse(userName);
  }
}

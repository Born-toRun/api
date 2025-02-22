package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.adapter.out.thirdparty.BornToRunAuthAdapter;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthSignInResponse;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthTokenResponse;
import kr.borntorun.api.adapter.out.thirdparty.model.RefreshTokenResponse;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.UserPort;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.CreateGuestCommand;
import kr.borntorun.api.domain.port.model.LoginResult;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignInCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;
import kr.borntorun.api.infrastructure.UserGateway;
import kr.borntorun.api.infrastructure.model.CreateGuestQuery;
import kr.borntorun.api.infrastructure.model.ModifyUserQuery;
import kr.borntorun.api.infrastructure.model.SignUpUserQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserPort {

	private final UserConverter userConverter;
	private final BornToRunAuthAdapter borntorunAuthAdapter;
	private final UserGateway userGateway;

	@Transactional
	@Override
	public LoginResult signIn(final SignInCommand command) {
		final AuthSignInResponse authSignInResponse = borntorunAuthAdapter.signIn(
		  userConverter.toAuthSignUpRequest(command));
		final AuthTokenResponse authTokenResponse = borntorunAuthAdapter.getToken(authSignInResponse.kakaoId());

		return new LoginResult(authTokenResponse.accessToken(), authSignInResponse.isMember());
	}

	@Transactional
	@Override
	public String signUp(final SignUpCommand command) {
		SignUpUserQuery query = userConverter.toSignUpUserQuery(command);
		return userGateway.modify(query);
	}

	@Transactional
	@Override
	public String getRefreshToken(String accessToken) {
		RefreshTokenResponse refreshTokenResponse = borntorunAuthAdapter.refreshToken(accessToken);
		return refreshTokenResponse.accessToken();
	}

	@Transactional
	@Override
	public void remove(final long userId) {
		userGateway.remove(userId);
	}

	@Transactional(readOnly = true)
	@Override
	public BornToRunUser searchById(final long userId) {
		UserEntity userEntity = userGateway.searchById(userId);
		return userConverter.toBornToRunUser(userEntity);
	}

	@Transactional(readOnly = true)
	@Override
	public BornToRunUser searchBySocialId(String socialId) {
		UserEntity userEntity = userGateway.searchBySocialId(socialId);
		return userConverter.toBornToRunUser(userEntity);
	}

	@Transactional
	@Override
	public BornToRunUser modify(final ModifyUserCommand command) {
		ModifyUserQuery query = userConverter.toModifyUserQuery(command);

		UserEntity modifiedUser = userGateway.modify(query);
		return userConverter.toBornToRunUser(modifiedUser);
	}

	@Transactional
	@Override
	public BornToRunUser createAndFlush(CreateGuestCommand command) {
		CreateGuestQuery query = userConverter.toCreateGuestQuery(command);
		UserEntity guest = userGateway.createAndFlush(query);
		return userConverter.toBornToRunUser(guest);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean exists(String socialId) {
		return userGateway.exists(socialId);
	}
}

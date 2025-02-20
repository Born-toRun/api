package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.ModifyUserRequest;
import kr.borntorun.api.adapter.in.web.payload.SignInRequest;
import kr.borntorun.api.adapter.in.web.payload.SignUpRequest;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.port.UserPort;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.LoginResult;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.RefreshTokenResult;
import kr.borntorun.api.domain.port.model.SignInCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserProxy {

	private final UserConverter userConverter;

	private final UserPort userPort;

	public LoginResult signIn(final SignInRequest request) {
		SignInCommand command = userConverter.toSignInCommand(request);
		return userPort.signIn(command);
	}

	@CacheEvict(allEntries = true)
	public String signUp(final TokenDetail my, final SignUpRequest request) {
		SignUpCommand command = userConverter.toSignUpCommand(request, my.getId());
		return userPort.signUp(command);
	}

	public RefreshTokenResult refreshToken(final String accessToken) {
		String refreshedToken = userPort.refreshToken(accessToken);
		return new RefreshTokenResult(refreshedToken);
	}

	@CacheEvict(allEntries = true)
	public void remove(final long myUserId) {
		userPort.remove(myUserId);
	}

	@Cacheable(key = "'search: ' + #my.hashCode()")
	public BornToRunUser search(final TokenDetail my) {
		return userPort.searchById(my.getId());
	}

	@Cacheable(key = "'search: ' + #userId")
	public BornToRunUser search(final long userId) {
		return userPort.searchById(userId);
	}

	@CacheEvict(allEntries = true)
	public BornToRunUser modify(final TokenDetail my, final ModifyUserRequest request) {
		ModifyUserCommand command = userConverter.toModifyUserCommand(request, my.getId());
		return userPort.modify(command);
	}
}

package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.ModifyUserRequest;
import kr.borntorun.api.adapter.in.web.payload.SignUpRequest;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.port.UserPort;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.RefreshTokenResult;
import kr.borntorun.api.domain.port.model.SignUpCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserProxy {

	private final UserConverter userConverter;

	private final UserPort userPort;

	@CacheEvict(allEntries = true)
	public String signUp(TokenDetail my, SignUpRequest request) {
		SignUpCommand command = userConverter.toSignUpCommand(request, my.getId());
		return userPort.signUp(command);
	}

	public RefreshTokenResult refreshToken(String accessToken) {
		String refreshedToken = userPort.getRefreshToken(accessToken);
		return new RefreshTokenResult(refreshedToken);
	}

	@CacheEvict(allEntries = true)
	public void remove(long myUserId) {
		userPort.remove(myUserId);
	}

	@Cacheable(key = "'search: ' + #my.hashCode()")
	public BornToRunUser search(TokenDetail my) {
		return userPort.searchById(my.getId());
	}

	@Cacheable(key = "'search: ' + #userId")
	public BornToRunUser search(long userId) {
		return userPort.searchById(userId);
	}

	@CacheEvict(allEntries = true)
	public BornToRunUser modify(TokenDetail my, ModifyUserRequest request) {
		ModifyUserCommand command = userConverter.toModifyUserCommand(request, my.getId());
		return userPort.modify(command);
	}
}

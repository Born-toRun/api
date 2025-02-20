package kr.borntorun.api.support.oauth.service;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.port.UserPort;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.CreateGuestCommand;
import kr.borntorun.api.support.oauth.entity.UserPrincipal;
import kr.borntorun.api.support.oauth.info.OAuth2UserInfo;
import kr.borntorun.api.support.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;

/*
access token 수신
 */
@Service
@RequiredArgsConstructor
public class BornToRunOAuth2UserService extends DefaultOAuth2UserService {

	private final UserPort userPort;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);

		try {
			return this.process(userRequest, user);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
		ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration()
		  .getRegistrationId()
		  .toUpperCase());

		OAuth2UserInfo socialUser = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
		BornToRunUser bornToRunUser = userPort.searchBySocialId(socialUser.getId());

		if (bornToRunUser == null) {
			bornToRunUser = createUser(socialUser, providerType);
		}

		return UserPrincipal.create(bornToRunUser, user.getAttributes());
	}

	private BornToRunUser createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
		CreateGuestCommand command = new CreateGuestCommand(userInfo.getId(), providerType);
		return userPort.create(command);
	}
}

package kr.borntorun.api.support.oauth.service;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.constant.RoleType;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.infrastructure.UserGateway;
import kr.borntorun.api.infrastructure.model.CreateUserQuery;
import kr.borntorun.api.support.oauth.entity.UserPrincipal;
import kr.borntorun.api.support.oauth.info.OAuth2UserInfo;
import kr.borntorun.api.support.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;

/*
 * 로그인시 user loading
 */
@Service
@RequiredArgsConstructor
public class BornToRunOAuth2UserService extends DefaultOAuth2UserService {

	private final UserGateway userGateway;

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
		UserEntity userEntity = userGateway.searchBySocialId(socialUser.getId());

		if (userEntity == null) {
			userEntity = createGuest(socialUser, providerType);
		}

		return UserPrincipal.create(userEntity, user.getAttributes());
	}

	private UserEntity createGuest(OAuth2UserInfo userInfo, ProviderType providerType) {
		CreateUserQuery query = new CreateUserQuery(userInfo.getId(), providerType, RoleType.GUEST);
		return userGateway.createAndFlush(query);
	}
}

package kr.borntorun.api.support.oauth.info;

import java.util.Map;

import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.support.oauth.info.impl.FacebookOAuth2UserInfo;
import kr.borntorun.api.support.oauth.info.impl.GoogleOAuth2UserInfo;
import kr.borntorun.api.support.oauth.info.impl.KakaoOAuth2UserInfo;
import kr.borntorun.api.support.oauth.info.impl.NaverOAuth2UserInfo;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
		return switch (providerType) {
			case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
			case FACEBOOK -> new FacebookOAuth2UserInfo(attributes);
			case NAVER -> new NaverOAuth2UserInfo(attributes);
			case KAKAO -> new KakaoOAuth2UserInfo(attributes);
			default -> throw new IllegalArgumentException("Invalid Provider Type.");
		};
    }
}

package kr.borntorun.api.support.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.infrastructure.UserGateway;
import kr.borntorun.api.support.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BornToRunUserDetailsService implements UserDetailsService {

	private final UserGateway userGateway;

	private final UserConverter userConverter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userGateway.searchBySocialId(username);
		return UserPrincipal.create(userEntity);
	}
}

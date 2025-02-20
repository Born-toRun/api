package kr.borntorun.api.support.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.borntorun.api.adapter.out.persistence.UserRepository;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.support.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BornToRunUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findBySocialId(username)
          .orElseThrow(() -> new UsernameNotFoundException("Can not find username."));
        BornToRunUser user = userConverter.toBornToRunUser(userEntity);
        return UserPrincipal.create(user);
    }
}

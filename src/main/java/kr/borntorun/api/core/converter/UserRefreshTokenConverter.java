package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;

import kr.borntorun.api.domain.port.model.CreateRefreshTokenCommand;
import kr.borntorun.api.infrastructure.model.CreateRefreshTokenQuery;

@Mapper(componentModel = "spring")
public interface UserRefreshTokenConverter {

	CreateRefreshTokenQuery toCreateRefreshTokenQuery(CreateRefreshTokenCommand source);
}

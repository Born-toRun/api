package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.YellowCardConverter;
import kr.borntorun.api.domain.port.YellowCardPort;
import kr.borntorun.api.domain.port.model.CreateYellowCardCommand;
import kr.borntorun.api.infrastructure.YellowCardGateway;
import kr.borntorun.api.infrastructure.model.CreateYellowCardQuery;
import kr.borntorun.api.support.exception.DuplicationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class YellowCardService implements YellowCardPort {

	private final YellowCardConverter yellowCardConverter;

	private final YellowCardGateway yellowCardGateway;

	@Transactional
	@Override
	public void create(final CreateYellowCardCommand command) {
		final boolean isExists = yellowCardGateway.exists(command.sourceUserId(), command.targetUserId());
		if (isExists) {
			throw new DuplicationException(
			  "이미 신고한 사용자입니다. [{" + command.sourceUserId() + " to " + command.targetUserId() + "}]");
		}

		CreateYellowCardQuery query = yellowCardConverter.toCreateYellowCardQuery(command);
		yellowCardGateway.create(query);
	}
}

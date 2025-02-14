package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.CreateYellowCardCommand;

public interface YellowCardPort {

  void create(final CreateYellowCardCommand command);
}
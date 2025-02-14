package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.CreateYellowCardRequest;
import kr.borntorun.api.core.converter.YellowCardConverter;
import kr.borntorun.api.core.service.YellowCardService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class YellowCardProxy {

  private final YellowCardService yellowCardService;

  public void create(final int myUserId, final CreateYellowCardRequest request) {
    yellowCardService.create(YellowCardConverter.INSTANCE.toCreateYellowCardCommand(request, myUserId));
  }
}

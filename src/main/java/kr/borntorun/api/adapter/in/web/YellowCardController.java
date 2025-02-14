package kr.borntorun.api.adapter.in.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.CreateYellowCardRequest;
import kr.borntorun.api.adapter.in.web.proxy.YellowCardProxy;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자 신고", description = "사용자 신고 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/yellow-cards")
public class YellowCardController {

  private final YellowCardProxy yellowCardProxy;

  @Operation(summary = "유저 신고하기", description = "해당 유저를 신고합니다.")
  @RequestMapping(value = "", method= RequestMethod.POST, produces="application/json;charset=UTF-8")
  public void CreateYellowCard(@AuthUser TokenDetail my, @RequestBody @Valid CreateYellowCardRequest request) {
    yellowCardProxy.create(my.getId(), request);
  }
}



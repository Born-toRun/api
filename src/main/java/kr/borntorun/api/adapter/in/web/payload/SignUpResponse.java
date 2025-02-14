package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "회원가입")
@Builder
public record SignUpResponse(@Schema(description = "등록된 회원명") String name) {}
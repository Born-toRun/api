package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "행사 출석")
public record AttendanceActivityRequest(@Schema(description = "참여코드") @NotNull int accessCode) {}
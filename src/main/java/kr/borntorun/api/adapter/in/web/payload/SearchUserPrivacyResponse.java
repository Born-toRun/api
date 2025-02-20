package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public record SearchUserPrivacyResponse(@Schema(description = "인스타그램 아이디 노출 동의 여부") Boolean isInstagramIdPublic) {
}

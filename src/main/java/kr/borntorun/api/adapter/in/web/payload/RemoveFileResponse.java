package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파일 삭제")
public record RemoveFileResponse(@Schema(description = "파일 식별자") long fileId) {
}

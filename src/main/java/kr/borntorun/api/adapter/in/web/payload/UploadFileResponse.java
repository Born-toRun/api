package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파일 업로드")
public record UploadFileResponse(@Schema(description = "파일 식별자")int fileId,
                                 @Schema(description = "cdn uri") String fileUri) {}

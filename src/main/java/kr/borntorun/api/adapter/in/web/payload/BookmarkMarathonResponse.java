package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "마라톤 대회 북마크")
public record BookmarkMarathonResponse(@Schema(description = "마라톤 식별자") long marathonId) {}
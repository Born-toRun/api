package kr.borntorun.api.adapter.in.web.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.constant.FeedCategory;

@Schema(description = "피드 작성")
public record CreateFeedRequest(@Schema(description = "업로드한 이미지 식별자 리스트") List<Long> imageIds,
                                @Schema(description = "내용") @NotNull String contents,
                                @Schema(description = "피드 종류") @NotNull FeedCategory category,
                                @Schema(description = "공개 범위") @NotNull FeedAccessLevel accessLevel) {}
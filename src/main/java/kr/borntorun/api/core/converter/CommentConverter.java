package kr.borntorun.api.core.converter;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.borntorun.api.adapter.in.web.payload.CreateCommentRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyCommentRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyCommentResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchCommentDetailResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchCommentResponse;
import kr.borntorun.api.domain.entity.CommentEntity;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.domain.port.model.CommentResult;
import kr.borntorun.api.domain.port.model.CreateCommentCommand;
import kr.borntorun.api.domain.port.model.ModifyCommentCommand;
import kr.borntorun.api.infrastructure.model.CreateCommentQuery;
import kr.borntorun.api.infrastructure.model.ModifyCommentQuery;

@Mapper(componentModel = "spring")
public interface CommentConverter {

	CreateCommentCommand toCreateCommentCommand(long myUserId, long feedId,
	  CreateCommentRequest source);

	CreateCommentQuery toCreateCommentQuery(CreateCommentCommand source);

	@Mapping(target = "userId", source = "myUserId")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "parentId", source = "parentCommentId")
	@Mapping(target = "registeredAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "feedEntity", ignore = true)
	@Mapping(target = "userEntity", ignore = true)
	@Mapping(target = "parent", ignore = true)
	@Mapping(target = "child", ignore = true)
	@Mapping(target = "recommendationEntities", ignore = true)
	CommentEntity toCommentEntity(CreateCommentQuery source);

	@Mapping(target = "writer", source = "source.userEntity", qualifiedByName = "convertCommentWriter")
	@Mapping(target = "reCommentQty", expression = "java(source.getChild().size())")
	@Mapping(target = "isMyComment", expression = "java(source.getUserId() == myUserId)")
	CommentResult toCommentResult(CommentEntity source, long myUserId);

	List<SearchCommentResponse.Comment> toSearchCommentResponseComment(List<CommentResult> source);

	@Mapping(target = "id", source = "source.id")
	@Mapping(target = "writer", expression = "java(new CommentDetail.Writer(source.getUserId(), source.getUserEntity().getName(), source.getUserEntity().getProfileImageUri(), source.getUserEntity().getCrewEntity().getName(), source.getUserEntity().getIsAdmin(), source.getUserEntity().getIsManager()))")
	CommentDetail toCommentDetail(CommentEntity source, List<CommentResult> reCommentResults);

	@Mapping(target = "writer", expression = "java(new SearchCommentDetailResponse.Writer(source.writer().userId(), source.writer().userName(), source.writer().profileImageUri(), source.writer().crewName(), source.writer().isAdmin(), source.writer().isManager()))")
	@Mapping(target = "reComments", source = "reCommentResults", qualifiedByName = "convertReComments")
	SearchCommentDetailResponse toSearchCommentDetailResponse(CommentDetail source, @Context long myUserId);

	List<SearchCommentDetailResponse.ReComment> toSearchCommentDetailResponseReComment(List<CommentResult> source,
	  @Context long myUserId);

	ModifyCommentCommand toModifyCommentCommand(ModifyCommentRequest source, long commentId);

	ModifyCommentQuery toModifyCommentQuery(ModifyCommentCommand source);

	@Mapping(target = "writer", source = "source.userEntity", qualifiedByName = "convertCommentWriter")
	@Mapping(target = "id", source = "id")
	@Mapping(target = "reCommentQty", ignore = true)
	@Mapping(target = "isMyComment", ignore = true)
	CommentResult toCommentResult(CommentEntity source);

	ModifyCommentResponse toModifyCommentResponse(CommentResult source);

	default List<CommentResult> toCommentResult(List<CommentEntity> source, long myUserId) {
		if (source == null) {
			return Collections.emptyList();
		}

		return source.stream()
		  .map(entity -> toCommentResult(entity, myUserId)) // 명확한 메서드 호출
		  .toList();
	}

	@Named("convertIsMyCommentByBornToRunUser")
	default boolean convertIsMyCommentByBornToRunUser(long userId, @Context long myUserId) {
		return userId == myUserId;
	}

	@Named("convertCommentWriter")
	default CommentResult.Writer convertCommentWriter(UserEntity source) {
		return new CommentResult.Writer(source.getId(), source.getName(), source.getProfileImageUri(),
		  source.getCrewEntity().getName(), source.getIsAdmin(), source.getIsManager());
	}

	@Named("convertReComments")
	default List<SearchCommentDetailResponse.ReComment> convertReComments(List<CommentResult> reCommentResults,
	  @Context long myUserId) {
		return toSearchCommentDetailResponseReComment(reCommentResults, myUserId);
	}
}

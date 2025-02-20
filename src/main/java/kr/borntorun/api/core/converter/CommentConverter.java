package kr.borntorun.api.core.converter;

import java.util.List;
import java.util.Map;

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
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.Comment;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.domain.port.model.CreateCommentCommand;
import kr.borntorun.api.domain.port.model.ModifyCommentCommand;
import kr.borntorun.api.infrastructure.model.CreateCommentQuery;
import kr.borntorun.api.infrastructure.model.ModifyCommentQuery;

@Mapper(componentModel = "spring")
public interface CommentConverter {

	CreateCommentCommand toCreateCommentCommand(final long myUserId, final long feedId,
	  final CreateCommentRequest source);

	CreateCommentQuery toCreateCommentQuery(final CreateCommentCommand source);

	@Mapping(target = "userId", source = "myUserId")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "parentId", ignore = true)
	@Mapping(target = "registeredAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "feedEntity", ignore = true)
	@Mapping(target = "userEntity", ignore = true)
	@Mapping(target = "recommendationEntities", ignore = true)
	CommentEntity toCommentEntity(final CreateCommentQuery source);

	@Mapping(target = "id", source = "source.id")
	@Mapping(target = "writer", expression = "java(new Comment.Writer(source.getUserId(), user.userName(), user.profileImageUri(), user.crewName(), user.isAdmin(), user.isManager()))")
	@Mapping(target = "reCommentQty", ignore = true)
	@Mapping(target = "isMyComment", source = "source.userId", qualifiedByName = "convertIsMyCommentByBornToRunUser")
	Comment toComment(final CommentEntity source, @Context final BornToRunUser user);

	@Mapping(target = "writer", source = "userId", qualifiedByName = "convertCommentWriter")
	@Mapping(target = "isMyComment", source = "source.userId", qualifiedByName = "convertIsMyComment")
	List<Comment> toComments(final List<CommentEntity> source,
	  @Context final Map<Long, BornToRunUser> writersByUserIdMapping, @Context final long myUserId);

	List<SearchCommentResponse.Comment> toSearchCommentResponseComment(final List<Comment> source);

	@Mapping(target = "id", source = "source.id")
	@Mapping(target = "writer", expression = "java(new CommentDetail.Writer(source.getUserId(), source.getUserEntity().getName(), source.getUserEntity().getProfileImageUri(), source.getUserEntity().getCrewEntity().getName(), source.getUserEntity().getIsAdmin(), source.getUserEntity().getIsManager()))")
	CommentDetail toCommentDetail(final CommentEntity source, final List<Comment> reComments);

	@Mapping(target = "writer", expression = "java(new SearchCommentDetailResponse.Writer(source.writer().userId(), source.writer().userName(), source.writer().profileImageUri(), source.writer().crewName(), source.writer().isAdmin(), source.writer().isManager()))")
	@Mapping(target = "reComments", source = "reComments", qualifiedByName = "convertReComments")
	SearchCommentDetailResponse toSearchCommentDetailResponse(final CommentDetail source, @Context final long myUserId);

	List<SearchCommentDetailResponse.ReComment> toSearchCommentDetailResponseReComment(final List<Comment> source,
	  @Context long myUserId);

	SearchCommentDetailResponse.ReComment toSearchCommentDetailResponseReComment(final Comment source,
	  @Context long myUserId);

	ModifyCommentCommand toModifyCommentCommand(final ModifyCommentRequest source, final long commentId);

	ModifyCommentQuery toModifyCommentQuery(final ModifyCommentCommand source);

	@Mapping(target = "writer", expression = "java(new Comment.Writer(source.getUserId(), source.getUserEntity().getName(), source.getUserEntity().getProfileImageUri(), source.getUserEntity().getCrewEntity().getName(), source.getUserEntity().getIsAdmin(), source.getUserEntity().getIsManager()))")
	@Mapping(target = "id", source = "id")
	@Mapping(target = "reCommentQty", ignore = true)
	@Mapping(target = "isMyComment", ignore = true)
	Comment toComment(final CommentEntity source);

	ModifyCommentResponse toModifyCommentResponse(final Comment source);

	@Named("convertIsMyCommentByBornToRunUser")
	default boolean convertIsMyCommentByBornToRunUser(final long userId, @Context final BornToRunUser user) {
		return userId == user.userId();
	}

	@Named("convertReComments")
	default List<SearchCommentDetailResponse.ReComment> convertReComments(final List<Comment> reComments,
	  @Context final long myUserId) {
		return toSearchCommentDetailResponseReComment(reComments, myUserId);
	}
}

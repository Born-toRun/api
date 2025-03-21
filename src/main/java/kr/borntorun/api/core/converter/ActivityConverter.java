package kr.borntorun.api.core.converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.borntorun.api.adapter.in.web.payload.AttendanceActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.AttendanceActivityResponse;
import kr.borntorun.api.adapter.in.web.payload.CreateActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.OpenActivityResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchActivityDetailResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchActivityResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchAllActivityRequest;
import kr.borntorun.api.domain.constant.ActivityRecruitmentType;
import kr.borntorun.api.domain.entity.ActivityEntity;
import kr.borntorun.api.domain.port.model.Activity;
import kr.borntorun.api.domain.port.model.AttendanceActivityCommand;
import kr.borntorun.api.domain.port.model.AttendanceResult;
import kr.borntorun.api.domain.port.model.CreateActivityCommand;
import kr.borntorun.api.domain.port.model.ModifyActivityCommand;
import kr.borntorun.api.domain.port.model.SearchAllActivityCommand;
import kr.borntorun.api.infrastructure.model.AttendanceActivityQuery;
import kr.borntorun.api.infrastructure.model.CreateActivityQuery;
import kr.borntorun.api.infrastructure.model.ModifyActivityQuery;
import kr.borntorun.api.infrastructure.model.SearchAllActivityQuery;
import kr.borntorun.api.support.TokenDetail;

@Mapper(componentModel = "spring")
public interface ActivityConverter {

	@Mapping(target = "myUserId", source = "my.id")
	CreateActivityCommand toCreateActivityCommand(CreateActivityRequest source, TokenDetail my);

	CreateActivityQuery toCreateActivityQuery(CreateActivityCommand source);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "registeredAt", ignore = true)
	@Mapping(target = "isOpen", ignore = true)
	@Mapping(target = "userEntity", ignore = true)
	@Mapping(target = "activityParticipationEntities", ignore = true)
	@Mapping(target = "userId", source = "myUserId")
	ActivityEntity toActivityEntity(CreateActivityQuery source);

	ModifyActivityCommand toModifyActivityCommand(ModifyActivityRequest source, long activityId);

	ModifyActivityQuery toModifyActivityQuery(ModifyActivityCommand source);

	@Mapping(target = "id", source = "source.id")
	@Mapping(target = "host", ignore = true)
	@Mapping(target = "participantsQty", ignore = true)
	@Mapping(target = "recruitmentType", ignore = true)
	Activity toActivity(ActivityEntity source, int attendanceCode);

	List<Activity> toActivityByUserId(List<ActivityEntity> source, @Context long myUserId);

	@Mapping(target = "recruitmentType", source = ".", qualifiedByName = "convertRecruitmentType")
	@Mapping(target = "attendanceCode", ignore = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "participantsQty", expression = "java(source.getActivityParticipationEntities().size())")
	@Mapping(target = "host", expression = "java(new Activity.Host(source.getUserId(), source.getUserEntity().getCrewEntity().getId(), source.getUserEntity().getProfileImageUri(), source.getUserEntity().getName(), source.getUserEntity().getCrewEntity().getName(), source.getUserEntity().getIsManager(), source.getUserEntity().getIsAdmin()))")
	Activity toActivityByUserId(ActivityEntity source, @Context long myUserId);

	List<SearchActivityResponse.Activity> toSearchActivityResponseActivity(List<Activity> source);

	@Mapping(target = "host", expression = "java(new SearchActivityResponse.Host(source.host().userId(), source.host().crewId(), source.host().userProfileUri(), source.host().userName(), source.host().crewName(), source.host().isManager(), source.host().isAdmin()))")
	SearchActivityResponse.Activity toSearchActivityResponseActivity(Activity source);

	@Mapping(target = "activityId", source = "id")
	OpenActivityResponse toOpenActivityResponse(Activity source);

	@Mapping(target = "myCrewId", source = "my.id")
	@Mapping(target = "myUserId", source = "my.id")
	@Mapping(target = "courses", source = "source.courses")
	@Mapping(target = "recruitmentType", source = "source.recruitmentType")
	SearchAllActivityCommand toSearchAllActivityCommand(SearchAllActivityRequest source, TokenDetail my);

	SearchAllActivityQuery toSearchAllActivityQuery(SearchAllActivityCommand source);

	@Mapping(target = "host", expression = "java(new SearchActivityDetailResponse.Host(source.host().userId(), source.host().crewId(), source.host().userProfileUri(), source.host().userName(), source.host().crewName(), source.host().isManager(), source.host().isAdmin()))")
	SearchActivityDetailResponse toSearchActivityDetailResponse(Activity source);

	AttendanceActivityCommand toAttendanceActivityCommand(AttendanceActivityRequest source, long activityId,
	  long myUserId);

	AttendanceActivityQuery toAttendanceActivityQuery(AttendanceActivityCommand source);

	@Mapping(target = "host", expression = "java(new AttendanceActivityResponse.Person(source.host().userId(), source.host().userName(), source.host().crewName(), source.host().userProfileUri()))")
	@Mapping(target = "participants", source = "participants", qualifiedByName = "convertParticipants")
	AttendanceActivityResponse toAttendanceActivityResponse(AttendanceResult source);

	@Named("convertParticipants")
	default List<AttendanceActivityResponse.Person> convertParticipants(
	  List<AttendanceResult.Participant> source) {
		return source.stream()
		  .map(e -> new AttendanceActivityResponse.Person(e.userId(),
			e.userName(),
			e.crewName(),
			e.userProfileUri()))
		  .collect(Collectors.toList());
	}

	@Named("convertRecruitmentType")
	default ActivityRecruitmentType convertRecruitmentType(ActivityEntity source, @Context long myUserId) {
		if (source.getActivityParticipationEntities().stream()
		  .anyMatch(e -> e.getUserEntity().getId() == myUserId)) {
			return ActivityRecruitmentType.ALREADY_PARTICIPATING;
		}

		if (source.getStartDate().isBefore(LocalDateTime.now())) {
			return ActivityRecruitmentType.ENDED;
		}

		if (source.getParticipantsLimit() >= source.getActivityParticipationEntities().size()) {
			return ActivityRecruitmentType.CLOSED;
		}

		return ActivityRecruitmentType.RECRUITING;
	}
}

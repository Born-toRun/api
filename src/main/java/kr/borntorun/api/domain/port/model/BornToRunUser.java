package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.constant.RoleType;

public record BornToRunUser(
    long userId,
    String socialId,
    ProviderType providerType,
    String refreshToken,
    RoleType roleType,
    String userName,
    Long crewId,
    String crewName,
    String instagramId,
    Long imageId,
    String profileImageUri,
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime lastLoginAt,
    Boolean isAdmin,
    Boolean isManager,
    int yellowCardQty,
    Boolean isInstagramIdPublic
) {
}
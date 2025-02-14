package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


public record BornToRunUser(
    int userId,
    String socialId,
    String userName,
    int crewId,
    String crewName,
    String ageRange,
    String birthday,
    String gender,
    String instagramId,
    int imageId,
    String profileImageUri,
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime lastLoginAt,
    Boolean isAdmin,
    Boolean isManager,
    int yellowCardQty,
    Boolean isGenderPublic,
    Boolean isBirthdayPublic,
    Boolean isInstagramIdPublic
) {
}
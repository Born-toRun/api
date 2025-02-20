package kr.borntorun.api.domain.port.model;

public record UserPrivacy(long id,
						  long userId,
                          Boolean isInstagramIdPublic) {}

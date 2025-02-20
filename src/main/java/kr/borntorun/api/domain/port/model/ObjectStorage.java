package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

public record ObjectStorage(long id,
							long userId,
                            String fileUri,
                            LocalDateTime uploadAt) {}

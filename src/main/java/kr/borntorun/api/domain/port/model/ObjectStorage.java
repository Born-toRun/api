package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

public record ObjectStorage(int id,
                            int userId,
                            String fileUri,
                            LocalDateTime uploadAt) {}

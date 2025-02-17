package kr.borntorun.api.adapter.in.web.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCrewRequest(@NotBlank @Size(min = 0, max = 255) String name,
								@NotBlank @Size(min = 0, max = 255) String contents,
								@NotBlank @Size(min = 0, max = 255) String sns,
								@NotBlank @Size(min = 0, max = 50) String region) {
}

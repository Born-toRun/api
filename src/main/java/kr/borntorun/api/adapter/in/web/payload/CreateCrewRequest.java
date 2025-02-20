package kr.borntorun.api.adapter.in.web.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCrewRequest(@NotBlank @Size(max = 255) String name,
								@NotBlank @Size(max = 255) String contents,
								@NotBlank @Size(max = 255) String sns,
								@NotBlank @Size(max = 50) String region) {
}

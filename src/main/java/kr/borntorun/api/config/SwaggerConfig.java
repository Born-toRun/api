package kr.borntorun.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
  info = @Info(title = "born-to-run web app",
	description = "born-to-run web app api명세",
	version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi chatOpenApi() {
		String[] paths = {"/api/**"};

		return GroupedOpenApi.builder()
		  .group("born-to-run WEB API v2")
		  .pathsToMatch(paths)
		  .build();
	}

	@Bean
	public OpenAPI customizeOpenAPI() {
		final String securitySchemeName = "Bearer Authentication";
		final String cookieSchemeName = "CookieAuth";

		return new OpenAPI()
		  .addSecurityItem(new SecurityRequirement()
			.addList(securitySchemeName)
			.addList(cookieSchemeName))
		  .components(new Components()
			.addSecuritySchemes(securitySchemeName, new SecurityScheme()
			  .name(securitySchemeName)
			  .type(Type.HTTP)
			  .scheme("bearer")
			  .bearerFormat("JWT"))
		  );
	}
}

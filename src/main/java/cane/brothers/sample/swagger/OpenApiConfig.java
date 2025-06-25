package cane.brothers.sample.swagger;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Hello Sample API", version = "1.0"), security = @SecurityRequirement(name = "oauth2"))
@SecuritySchemes(value = @SecurityScheme(name = "oauth2", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                scopes = {@OAuthScope(name = "openid")}))))
class OpenApiConfig {
}

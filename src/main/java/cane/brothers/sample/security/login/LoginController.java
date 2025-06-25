package cane.brothers.sample.security.login;

import static org.springdoc.core.utils.Constants.SWAGGER_UI_PATH;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Hidden
@Slf4j
@RestController
@RequiredArgsConstructor
class LoginController {

    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;

    @GetMapping(value = "/")
    void swaggerUiRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(swaggerUiPath);
    }

    // shortcut to Roche SSO
    @GetMapping(value = "/login")
    void userLoginRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/cognito");
    }

    @GetMapping("favicon.ico")
    void returnNoFavicon() {
        // Gracefully Disable Favicon
    }
}

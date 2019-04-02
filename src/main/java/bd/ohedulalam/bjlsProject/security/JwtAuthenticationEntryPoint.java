package bd.ohedulalam.bjlsProject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* This class is used by spring security when an unauthenticated
* user wants to access a authenticated resource.
* To do so, it implements AuthenticationEntryPoint interface and
* implement the commence method which provides above exception handling.
*/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //Here Logger is used to print exception on console.
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e)
            throws IOException, ServletException {

        logger.error("Responding with an unauthorized error message- {}", e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}

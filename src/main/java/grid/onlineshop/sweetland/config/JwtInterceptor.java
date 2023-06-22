package grid.onlineshop.sweetland.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

    private static final String AUTH_HEADER = "Authorization";

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler)
            throws Exception {
        String token = getTokenFromRequest(request);
        if (token != null) {
            // Add the token to the headers
            response.addHeader(AUTH_HEADER, "Bearer " + token);
        }
        return true;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}


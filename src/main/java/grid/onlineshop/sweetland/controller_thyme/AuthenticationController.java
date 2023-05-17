package grid.onlineshop.sweetland.controller_thyme;

import grid.onlineshop.sweetland.config.JwtService;
import grid.onlineshop.sweetland.dto.request.AddUserDto;
import grid.onlineshop.sweetland.dto.request.LoginRequest;
import grid.onlineshop.sweetland.dto.response.AuthenticationResponse;
import grid.onlineshop.sweetland.exceptions.userexc.UserAlreadyExistsException;
import grid.onlineshop.sweetland.exceptions.userexc.UserNotFoundException;
import grid.onlineshop.sweetland.model.User;
import grid.onlineshop.sweetland.service.AuthenticationService;
import grid.onlineshop.sweetland.service.LogoutService;
import grid.onlineshop.sweetland.service.UserService;
import grid.onlineshop.sweetland.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final LogoutService logoutService;

    private final CookieSerializer cookieSerializer;


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/register")
    public String register(@ModelAttribute("user") AddUserDto user, Model model) {
        try {
            authenticationService.addCustomer(user);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", "User with this email already exists!");
            return "login";
        }

        return "products";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("user") LoginRequest user, HttpServletResponse response, Model model) throws UserNotFoundException {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(user);
        if (authenticationResponse == null) {
            model.addAttribute("error", "Invalid email or password");
            logger.info("invalid");
            return "login";
        } else {
            // authentication succeeded, store JWT token in a cookie
            Cookie jwtCookie = new Cookie("JWT", authenticationResponse.getAccessToken());
            jwtCookie.setHttpOnly(true);
            response.addCookie(jwtCookie);

            // redirect user to appropriate page based on role
            String username = jwtService.extractUsername(authenticationResponse.getAccessToken());
            String name = userService.findNameByEmail(username);
            User findUser = userService.getByEmail(username);

            String role = findUser.getRole().toString();
            if (role.equals("ADMIN")) {
                return "redirect:/admin";
            } else {

                return "products";
            }
        }
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        logoutService.logout(request, response, auth);
//        return "redirect:/welcome";
//    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session and delete the cookie
        request.getSession().invalidate();
        CookieUtils.deleteCookie(request, response, "JWT");

        // Redirect the user to the login page
        return "redirect:/login";
    }

//    @PostMapping("/authenticate")
//    public AuthenticationResponse authenticate(@RequestBody @Valid final LoginRequest authenticationRequest) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
//        } catch (final BadCredentialsException ex) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getLogin());
//        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//        authenticationResponse.setAccessToken(jwtTokenService.generateToken(userDetails));
//        return authenticationResponse;
//    }


//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Cookie cookie = new Cookie("jwt", null);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//
//        return "redirect:/welcome";
//    }


}

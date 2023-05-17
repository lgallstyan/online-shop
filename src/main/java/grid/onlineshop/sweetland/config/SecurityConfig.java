package grid.onlineshop.sweetland.config;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.IOException;
//import java.util.Set;
//
//import static grid.onlineshop.sweetland.util.enums.Role.ADMIN;
//import static org.springframework.http.HttpMethod.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static grid.onlineshop.sweetland.util.enums.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;


    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
//                        "/admin/**",
                        "/user",
                        "/show-cart"
                )
                .authenticated()

//                .requestMatchers("/admin/**").hasRole(ADMIN.name())
                .requestMatchers("/user").hasRole(USER.name())

//                .requestMatchers(GET, "/admin/**").hasAnyAuthority(ADMIN_READ.name())
//                .requestMatchers(POST, "/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
//                .requestMatchers(PUT, "/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
//                .requestMatchers(DELETE, "/admin/**").hasAnyAuthority(ADMIN_DELETE.name())


                .anyRequest()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .authenticationProvider(adminAuthenticationProvider)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                .loginPage("/login")
//                .failureForwardUrl("/welcome")
//                .defaultSuccessUrl("/home")
//                .permitAll()
//                .and()
                .logout()
                .logoutUrl("/logout") // Configure the logout URL
                .logoutSuccessUrl("/") // Configure the logout success URL
                .addLogoutHandler(logoutHandler)
//                .logoutSuccessUrl()
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }


//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() {
//        return (request, response, authentication) -> response.sendRedirect("/sweet/");
//
//    }


}


//
//
////                    .anyRequest()
////                    .authenticated()
////                    .and()
////                    .sessionManagement()
////                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                    .and()
////                    .authenticationProvider(authenticationProvider)
////                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
////                    .logout()
////                    .logoutUrl("/api/v1/auth/logout")
////                    .addLogoutHandler(logoutHandler)
////                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
////            ;
//
//            return http.build();
//
////        http
////                .csrf()
////                .disable()
////                .authorizeHttpRequests()
////                .requestMatchers("/logged").hasRole("USER")
////                .requestMatchers("/admin-dashboard").hasRole("ADMIN")
////                .requestMatchers("/**")
////                .permitAll()
////                .anyRequest()
////                .authenticated()
////                .and()
////                .formLogin()
////                .successHandler(successHandler())
////                .loginPage("/logged")   //the user tries to access a secured resource without being authenticated
//////                .loginProcessingUrl("/logged") // the endpoint that handles the login request
////                .defaultSuccessUrl("/admin-dashboard", true)
////                .and()//
////                .logout()
////                .logoutUrl("/logout")
////                .logoutSuccessHandler(logoutSuccessHandler())
////                .invalidateHttpSession(true)
////                .deleteCookies("JSESSIONID")
////                .and()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .authenticationProvider(authenticationProvider)
////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
//
//
//    }
//


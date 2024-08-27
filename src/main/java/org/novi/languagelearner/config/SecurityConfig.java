package org.novi.languagelearner.config;
import org.novi.languagelearner.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// TODO: testen of je user kan maken en inloggen

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: beter om hier per controller te bepalen welke rol toegang heeft? Dus /controller/admin**
    // TODO: Alles authenticated maken behalve login en register
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity, JwtRequestFilter jwtRequestFilter) throws Exception{
        httpSecurity
                // hp.disable schakelt uit dat er toegang kan worden verkregen met username en password. Je moet een jwt token hebben. Daar zorgt de .addFilterBefore voor.
                .httpBasic(hp -> hp.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/login").permitAll()

                        .requestMatchers(HttpMethod.POST,"/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/users/admin**").hasRole("ADMIN")
                        .requestMatchers("/users").hasRole("USER")
                        .requestMatchers("/users/**").hasRole("USER")

                        .requestMatchers("/photo").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/exercises/admin**").hasRole("ADMIN")

                        .requestMatchers("/answer").hasRole("USER")
                        .requestMatchers("/answer/admin").hasRole("ADMIN")

                        .requestMatchers("/groups/admin").hasRole("ADMIN")
                        .requestMatchers("/groups**").hasRole("USER")

                        .requestMatchers("/stats/user/exercise").hasRole("USER")
                        .requestMatchers("/stats/user**").hasRole("USER")
                        .requestMatchers("/stats/admin").hasRole("ADMIN")

                        .anyRequest().permitAll()
                        )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                // de lege lambda zorgt ervoor dat de default CORS configuratie is
                .cors(cors -> cors.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
                return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        var builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }

}



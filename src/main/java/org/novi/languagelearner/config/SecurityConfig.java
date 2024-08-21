package org.novi.languagelearner.config;
import org.novi.languagelearner.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity, JwtRequestFilter jwtRequestFilter) throws Exception{
        httpSecurity
                // hp.disable schakelt uit dat er toegang kan worden verkregen met username en password. Je moet een jwt token hebben. Daar zorgt de .addFilterBefore voor.
                .httpBasic(hp -> hp.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/users/profile").hasRole("USER")
                        .requestMatchers("/upload**").permitAll()
                        .requestMatchers("/upload/**").permitAll()
                        .requestMatchers("/upload/1").permitAll()
                        .requestMatchers("/upload/photo").hasRole("USER")
                        .requestMatchers("/exercises**").permitAll()
                        .requestMatchers("/exercises/**").permitAll()
                        .requestMatchers("/exercises/delete/{id}").permitAll()
                        .requestMatchers("/exercises/").permitAll()
                        .requestMatchers("/answer-validation/**").permitAll()
                        .requestMatchers("/answer-validation**").permitAll()
                        .requestMatchers("/groups").permitAll()
                        .requestMatchers("/groups/upload-json-files").permitAll()
                        .requestMatchers("/groups/all").permitAll()
                        .requestMatchers("/groups**").permitAll()
                        .requestMatchers("/groups/**").permitAll()
                        .requestMatchers("/groups/delete/{id}").permitAll()
                        .requestMatchers("/stats").permitAll()
                        .requestMatchers("/stats**").permitAll()
                        .requestMatchers("/stats/**").permitAll()
                        .requestMatchers("/stats/user/all").permitAll()
                        .requestMatchers("/stats/admin**").hasRole("ADMIN")
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/public/more").permitAll()
                        .requestMatchers("/secure/**").permitAll()
                        .requestMatchers("/secure/admin").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers("/secure/user").permitAll()
                        .requestMatchers("/login**").permitAll()
                        .requestMatchers("/practice**").hasRole("USER")

                        .anyRequest().denyAll()
                        )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                // de lege lambda zorgt ervoor dat de default CORS configuratie
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



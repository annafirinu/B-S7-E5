package it.epicode.B_S7_E5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.formLogin(http->http.disable());
        httpSecurity.csrf(http->http.disable());
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.cors(Customizer.withDefaults());

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET,"/studenti/**").permitAll());

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/eventi/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/utenti/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/prenotazioni/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.POST).permitAll());

        httpSecurity.authorizeHttpRequests(http->http.anyRequest().denyAll());

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}


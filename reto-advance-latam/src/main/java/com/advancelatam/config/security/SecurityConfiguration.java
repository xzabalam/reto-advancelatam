package com.advancelatam.config.security;

import com.advancelatam.common.enumeration.RolEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration implements WebMvcConfigurer {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**", "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and().csrf().disable().headers().frameOptions().disable().and().authorizeRequests()
                // Configurar el acceso libre a swagger ui
                .antMatchers(AUTH_WHITELIST).permitAll()
                // Configurar el acceso libre a la consola de h2
                .antMatchers("/h2-console/**").permitAll()
                // Configurar el a la página de autenticacaión
                // Configurar el a la página de autenticacaión
                .antMatchers("/api/auth").hasAnyAuthority(RolEnum.ROLE_ADMINISTRADOR.name(), RolEnum.ROLE_USUARIO.name())
                // Configurar el acceso al api rest para obtener los datos de un usuarioS
                .antMatchers("/api/usuarios/**").hasAuthority(RolEnum.ROLE_ADMINISTRADOR.name())
                // Configurar el acceso al api rest para configurar los horarios del pico y placa
                .antMatchers(HttpMethod.GET, "/api/pico-placa/verificar-circulacion/**").permitAll()
                .antMatchers("/api/pico-placa/**").hasAuthority(RolEnum.ROLE_ADMINISTRADOR.name())
                // Configurar el acceso al api rest para configurar los autos
                .antMatchers("/api/auto/**").permitAll()
                // Configurar el acceso al api rest para configurar los tipos de autos
                .antMatchers("/api/tipo-auto/**").permitAll()
                // todas las dem[as deben estar autenticadas.
                .anyRequest().authenticated().and().httpBasic().and().headers().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.ORIGIN);
        return http.build();
    }
}

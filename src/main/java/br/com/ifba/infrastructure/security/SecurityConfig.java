package br.com.ifba.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuração principal para o Spring Security.
 * <p>
 * A anotação {@code @Configuration} indica que esta classe contém definições de beans.
 * A anotação {@code @EnableWebSecurity} habilita a integração do Spring Security com o Spring MVC.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define um Bean para o PasswordEncoder, que será usado para criptografar as senhas dos usuários.
     * <p>
     * O BCrypt é o algoritmo de hashing de senhas recomendado por sua força e capacidade
     * de se adaptar ao aumento do poder de processamento ao longo do tempo.
     * Este Bean estará disponível para injeção em outras partes da aplicação, como no UserService.
     *
     * @return uma instância de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança que processará as requisições HTTP.
     * <p>
     * Esta configuração define as regras de autorização para os endpoints da API.
     *
     * @param http o objeto {@link HttpSecurity} para configurar a segurança.
     * @return o {@link SecurityFilterChain} construído.
     * @throws Exception se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // NOTA: Esta é uma configuração de desenvolvimento.
        // Ela desabilita a proteção CSRF (comum para APIs REST stateless)
        // e permite o acesso a TODAS as requisições sem autenticação.
        // Isso será alterado quando a autenticação JWT for implementada.
        http.csrf(csrf -> csrf.disable()) // Desabilita a proteção contra Cross-Site Request Forgery
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas as requisições
                );
        return http.build();
    }
}
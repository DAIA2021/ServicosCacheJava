package com.soulcode.Servicos.Config;

import com.soulcode.Servicos.Security.JWTAuthenticationFilter;
import com.soulcode.Servicos.Security.JWTAuthorizationFilter;
import com.soulcode.Servicos.Services.AuthUserDetailService;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
// Agrega todas as informações de segurança http, e gerência do user.

@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {
    //Unificar as classes do backend
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailService userDetailService; // do SPRING
    //carregar o usuário no banco



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // habilita o cors e desabilita o csrf
        http.cors().and().csrf().disable();
        // JWTAuthenticationFilter é chamado quando uso /login
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils));

        http.authorizeRequests() // autoriza requisições
                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers(HttpMethod.GET, "/servicos/**").permitAll() // ** representa qualquer possibilidade
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    // TODO: explicação do restante e do Redis Cache
    @Bean
    // CROSS ORIGIN RESOURCE SHARING
    CorsConfigurationSource corsConfigurationSource() { //configuração global de CORS
        CorsConfiguration configuration = new CorsConfiguration(); // configurações padrões
        configuration.setAllowedMethods(List.of( //quais metodos estão liberados via Cors
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        )); // métodos permitidos para o front acessar
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // endpoints permitidos para o front acessar
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
        //"servicos/funcionarios" -> /** todos os endpoints
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


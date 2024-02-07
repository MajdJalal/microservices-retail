package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
@Configuration
@EnableWebFluxSecurity /*remeber as Api GAteway spring boot is based on web flux project not springWebMVC*/
public class SecurityConfig {

    //lets enable secuirty cong in api gateway
    //i removed csrf as it is deprecated
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity

                .authorizeExchange(exchange -> exchange
                        .pathMatchers("eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return serverHttpSecurity.build();
    }

}

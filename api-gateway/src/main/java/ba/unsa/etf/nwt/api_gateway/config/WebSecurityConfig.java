package ba.unsa.etf.nwt.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * WebSecurityConfig class
 **/


@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                    // user-serice
                    .pathMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll() //login
                    .pathMatchers(HttpMethod.GET,"/api/users/**").hasRole("Admin") //korisnike može samo admin vidjeti
                    .pathMatchers(HttpMethod.DELETE,"/api/users/**").hasRole("Admin") //korisnike može samo admin brisati
                    .pathMatchers("/api/roles/**").hasRole("Admin") // samo admin može pristupiti rolama
                    // recipe-service
                    .pathMatchers(HttpMethod.POST,"/api/categorys/**").hasRole("Admin")
                    .pathMatchers(HttpMethod.PUT,"/api/categorys/**").hasRole("Admin")
                    .pathMatchers(HttpMethod.DELETE,"/api/categorys").denyAll()//hasRole("Admin")
                    .pathMatchers(HttpMethod.DELETE,"/api/categorys/**").hasRole("Admin")
                    .pathMatchers(HttpMethod.GET,"/api/pictures").hasRole("Admin") // sve slike može samo admin vidjeti
                    .pathMatchers(HttpMethod.DELETE,"/api/pictures").denyAll()//hasRole("Admin") // sve slike može samo admin obrisati
                    .pathMatchers(HttpMethod.DELETE,"/api/recipes").denyAll()//hasRole("Admin") // sve recepte može samo admin obrisati
                    .pathMatchers(HttpMethod.GET,"/api/steps").hasRole("Admin") // sve korake može samo admin vidjeti
                    .pathMatchers(HttpMethod.DELETE,"/api/steps").denyAll()//hasRole("Admin") // sve korake može samo admin obrisati
                    // rating-service
                    .pathMatchers(HttpMethod.GET,"/api/ratings").hasRole("Admin")
                    // ingredients-service
                    .pathMatchers(HttpMethod.GET,"/api/ingredients").hasRole("Admin")
                    .pathMatchers(HttpMethod.POST,"/api/ingredients/**").hasRole("Admin")
                    .pathMatchers(HttpMethod.PUT,"/api/ingredients/**").hasRole("Admin")
                    .pathMatchers(HttpMethod.DELETE,"/api/ingredients").denyAll()//hasRole("Admin") // niko ne može sve obrisati
                    .pathMatchers(HttpMethod.DELETE,"/api/ingredients/**").hasRole("Admin")
                    .pathMatchers(HttpMethod.GET,"/api/ingredientRecipes").hasRole("Admin")
                    .pathMatchers(HttpMethod.DELETE,"/api/ingredientRecipes").denyAll()
                    .pathMatchers(HttpMethod.GET,"/api/ingredientPictures").hasRole("Admin")
                    .pathMatchers(HttpMethod.DELETE,"/api/ingredientPictures").denyAll()
                    .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> {
                    System.out.println("[1] Authentication error: Unauthorized[401]: " + e.getMessage());

                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                })
                .accessDeniedHandler((swe, e) -> {
                    System.out.println("[2] Authentication error: Access Denied[401]: " + e.getMessage());

                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                })
                .and()
//                .addFilterAt(new JwtTokenAuthenticationFilter(jwtConfig),SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
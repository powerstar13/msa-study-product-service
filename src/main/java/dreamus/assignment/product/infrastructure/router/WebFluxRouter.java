package dreamus.assignment.product.infrastructure.router;

import dreamus.assignment.product.presentation.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@EnableWebFlux
public class WebFluxRouter implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(3600);
    }

    @Bean
    public RouterFunction<ServerResponse> routerBuilder(ProductHandler productHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.LAYOUT_ROOT.getPath(), builder1 ->
                builder1.nest(accept(MediaType.APPLICATION_JSON), builder2 ->
                    builder2
                        .POST(RouterPathPattern.LAYOUT_REGISTER.getPath(), productHandler::layoutRegister) // 레이아웃 등록
                )
            )
            .build();
    }
}

package msa.study.product.infrastructure.router;

import msa.study.product.presentation.ProductHandler;
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
            .path(RouterPathPattern.LAYOUT_PRODUCT_ROOT.getPath(), builder1 ->
                builder1.nest(accept(MediaType.APPLICATION_JSON), builder2 ->
                    builder2
                        .POST(RouterPathPattern.LAYOUT_PRODUCT_REGISTER.getPath(), productHandler::layoutProductRegister) // 레이아웃 상품 등록
                        .PUT(RouterPathPattern.LAYOUT_PRODUCT_MODIFY.getPath(), productHandler::layoutProductModify) // 레이아웃 상품 수정
                        .DELETE(RouterPathPattern.LAYOUT_PRODUCT_DELETE.getPath(), productHandler::layoutProductDelete) // 레이아웃 상품 삭제
                )
            )
            .path(RouterPathPattern.LAYOUT_PRODUCT_ROOT.getPath(), builder ->
                builder
                    .GET(RouterPathPattern.LAYOUT_PRODUCT_INFO.getPath(), productHandler::layoutProductInfo) // 레이아웃 상품 정보 조회
                    .GET(RouterPathPattern.LAYOUT_PRODUCT_LIST.getPath(), productHandler::layoutProductList) // 레이아웃 상품 목록 조회
            )
            .build();
    }
}

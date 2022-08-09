package dreamus.assignment.product.infrastructure.factory;

import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.presentation.request.LayoutRegisterRequest;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestFactory {

    private static final String layoutName = "레이아웃 이름";

    public static String getLayoutName() {
        return layoutName;
    }

    public static Layout layout() {

        return Layout.builder()
            .layoutId(UUID.randomUUID().toString())
            .name(layoutName)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Mono<Layout> layoutMono() {
        return Mono.just(layout());
    }

    public static ProductDTO.LayoutIdInfo layoutIdInfoDTO() {

        return ProductDTO.LayoutIdInfo.builder()
            .layoutId(UUID.randomUUID().toString())
            .build();
    }

    public static Mono<ProductDTO.LayoutIdInfo> layoutIdInfoDTOMono() {
        return Mono.just(layoutIdInfoDTO());
    }

    public static LayoutRegisterRequest layoutRegisterRequest() {

        return LayoutRegisterRequest.builder()
            .name(layoutName)
            .build();
    }
}

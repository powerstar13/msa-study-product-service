package dreamus.assignment.product.infrastructure.factory;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.Product;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.presentation.request.LayoutProductRegisterRequest;
import dreamus.assignment.product.presentation.request.dto.ProductRegisterRequestDTO;
import org.apache.commons.lang3.RandomUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class TestFactory {

    private static final String layoutName = "레이아웃 이름";
    private static final String productName = "상품명";
    private static final int productPrice = 100;

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

    public static Product product() {

        return Product.builder()
            .name(productName)
            .price(productPrice)
            .layoutId(UUID.randomUUID().toString())
            .build();
    }

    public static Flux<Product> productFlux() {
        return Flux.just(product(), product());
    }

    public static ProductDTO.LayoutIdInfo layoutIdInfoDTO() {

        return ProductDTO.LayoutIdInfo.builder()
            .layoutId(UUID.randomUUID().toString())
            .build();
    }

    public static Mono<ProductDTO.LayoutIdInfo> layoutIdInfoDTOMono() {
        return Mono.just(layoutIdInfoDTO());
    }

    public static ProductCommand.ProductRegister productRegisterCommand() {

        return ProductCommand.ProductRegister.builder()
            .name(productName)
            .price(productPrice)
            .build();
    }

    public static ProductCommand.LayoutProductRegister layoutProductRegisterCommand() {

        return ProductCommand.LayoutProductRegister.builder()
            .name(layoutName)
            .productList(Arrays.asList(productRegisterCommand(), productRegisterCommand()))
            .build();
    }

    public static ProductRegisterRequestDTO productRegisterRequestDTO() {

        return ProductRegisterRequestDTO.builder()
            .name(productName)
            .price(productPrice)
            .build();
    }

    public static LayoutProductRegisterRequest layoutProductRegisterRequest() {

        return LayoutProductRegisterRequest.builder()
            .name(layoutName)
            .productList(Arrays.asList(productRegisterRequestDTO(), productRegisterRequestDTO()))
            .build();
    }
}

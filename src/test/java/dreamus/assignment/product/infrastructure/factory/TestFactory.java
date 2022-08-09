package dreamus.assignment.product.infrastructure.factory;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.Product;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.presentation.request.LayoutProductModifyRequest;
import dreamus.assignment.product.presentation.request.LayoutProductRegisterRequest;
import dreamus.assignment.product.presentation.request.dto.ProductModifyRequestDTO;
import dreamus.assignment.product.presentation.request.dto.ProductRegisterRequestDTO;
import dreamus.assignment.product.presentation.response.LayoutProductInfoResponse;
import dreamus.assignment.product.presentation.response.LayoutProductListResponse;
import dreamus.assignment.product.presentation.response.dto.LayoutProductResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    public static Flux<Layout> layoutFlux() {
        return Flux.just(layout(), layout());
    }

    public static Product product() {

        return Product.builder()
            .productId(UUID.randomUUID().toString())
            .name(productName)
            .price(productPrice)
            .layoutId(UUID.randomUUID().toString())
            .build();
    }

    public static List<Product> productList() {
        return Arrays.asList(product(), product());
    }

    public static Flux<Product> productFlux() {
        return Flux.fromIterable(productList());
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

    public static ProductDTO.LayoutProductAggregate layoutProductAggregateDTO() {

        return ProductDTO.LayoutProductAggregate.builder()
            .layout(layout())
            .productList(productList())
            .build();
    }

    public static Mono<ProductDTO.LayoutProductAggregate> layoutProductAggregateDTOMono() {
        return Mono.just(layoutProductAggregateDTO());
    }

    public static ProductCommand.ProductModify productModifyCommand() {

        return ProductCommand.ProductModify.builder()
            .productId(UUID.randomUUID().toString())
            .name(productName)
            .price(productPrice)
            .build();
    }

    public static ProductCommand.LayoutProductModify layoutProductModifyCommand() {

        return ProductCommand.LayoutProductModify.builder()
            .layoutId(UUID.randomUUID().toString())
            .name(layoutName)
            .productList(Arrays.asList(productModifyCommand(), productModifyCommand()))
            .build();
    }

    public static ProductModifyRequestDTO productModifyRequestDTO() {

        return ProductModifyRequestDTO.builder()
            .productId(UUID.randomUUID().toString())
            .name(productName)
            .price(productPrice)
            .build();
    }

    public static LayoutProductModifyRequest layoutProductModifyRequest() {

        return LayoutProductModifyRequest.builder()
            .layoutId(UUID.randomUUID().toString())
            .name(layoutName)
            .productList(Arrays.asList(productModifyRequestDTO(), productModifyRequestDTO()))
            .build();
    }

    public static ProductDTO.ProductInfo productInfoDTO() {

        return ProductDTO.ProductInfo.builder()
            .productId(UUID.randomUUID().toString())
            .name(productName)
            .price(productPrice)
            .build();
    }

    public static ProductDTO.LayoutProductInfo layoutProductInfoDTO() {

        return ProductDTO.LayoutProductInfo.builder()
            .layoutId(UUID.randomUUID().toString())
            .name(layoutName)
            .productList(Arrays.asList(productInfoDTO(), productInfoDTO()))
            .build();
    }

    public static Mono<ProductDTO.LayoutProductInfo> layoutProductInfoDTOMono() {
        return Mono.just(layoutProductInfoDTO());
    }

    public static LayoutProductResponseDTO.ProductInfo productInfoResponseDTO() {

        return LayoutProductResponseDTO.ProductInfo.builder()
            .productId(UUID.randomUUID().toString())
            .name(productName)
            .price(productPrice)
            .build();
    }

    public static LayoutProductInfoResponse layoutProductInfoResponse() {

        return LayoutProductInfoResponse.builder()
            .layoutId(UUID.randomUUID().toString())
            .name(layoutName)
            .productList(Arrays.asList(productInfoResponseDTO(), productInfoResponseDTO()))
            .build();
    }

    public static ProductDTO.LayoutProductList layoutProductListDTO() {

        return ProductDTO.LayoutProductList.builder()
            .layoutProductList(Arrays.asList(layoutProductInfoDTO(), layoutProductInfoDTO()))
            .build();
    }

    public static Mono<ProductDTO.LayoutProductList> layoutProductListDTOMono() {
        return Mono.just(layoutProductListDTO());
    }

    public static LayoutProductResponseDTO.LayoutProductInfo layoutProductInfoResponseDTO() {

        return LayoutProductResponseDTO.LayoutProductInfo.builder()
            .layoutId(UUID.randomUUID().toString())
            .name(layoutName)
            .productList(Arrays.asList(productInfoResponseDTO(), productInfoResponseDTO()))
            .build();
    }

    public static LayoutProductListResponse layoutProductListResponse() {

        return LayoutProductListResponse.builder()
            .layoutProductList(Arrays.asList(layoutProductInfoResponseDTO(), layoutProductInfoResponseDTO()))
            .build();
    }
}

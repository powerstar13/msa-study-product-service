package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.domain.service.dto.ProductDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductReader productReader;
    @MockBean
    private ProductStore productStore;
    @MockBean
    private ProductDTOMapper productDTOMapper;

    @DisplayName("레이아웃 상품 등록")
    @Test
    void layoutProductRegister() {

        given(productReader.layoutExistCheck(anyString())).willReturn(Mono.empty());
        given(productStore.layoutProductRegister(any(ProductCommand.LayoutProductRegister.class))).willReturn(layoutMono());

        Mono<ProductDTO.LayoutIdInfo> result = productService.layoutProductRegister(layoutProductRegisterCommand());

        StepVerifier.create(result.log())
            .assertNext(layoutIdInfo -> assertNotNull(layoutIdInfo.getLayoutId()))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 수정")
    @Test
    void layoutProductModify() {

        given(productReader.findLayoutProductAggregate(anyString())).willReturn(layoutProductAggregateDTOMono());
        given(productStore.layoutProductModify(any(ProductDTO.LayoutProductAggregate.class), any(ProductCommand.LayoutProductModify.class))).willReturn(Mono.empty());

        Mono<Void> result = productService.layoutProductModify(layoutProductModifyCommand());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 정보 조회")
    @Test
    void layoutProductInfo() {

        given(productReader.findLayoutProductAggregate(anyString())).willReturn(layoutProductAggregateDTOMono());
        given(productDTOMapper.of(any(Layout.class), anyList())).willReturn(layoutProductInfoDTO());

        Mono<ProductDTO.LayoutProductInfo> result = productService.layoutProductInfo(UUID.randomUUID().toString());

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 목록 조회")
    @Test
    void layoutProductList() {

        given(productReader.findAllLayoutProduct()).willReturn(layoutProductListDTOMono());

        Mono<ProductDTO.LayoutProductList> result = productService.layoutProductList();

        StepVerifier.create(result.log())
            .assertNext(layoutProductList -> assertNotNull(layoutProductList.getLayoutProductList()))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 삭제")
    @Test
    void layoutProductDelete() {

        given(productReader.findLayoutProductAggregate(anyString())).willReturn(layoutProductAggregateDTOMono());
        given(productStore.layoutProductDelete(any(ProductDTO.LayoutProductAggregate.class))).willReturn(Mono.empty());

        Mono<Void> result = productService.layoutProductDelete(UUID.randomUUID().toString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}
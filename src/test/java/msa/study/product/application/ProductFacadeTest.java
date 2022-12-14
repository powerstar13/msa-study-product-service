package msa.study.product.application;

import msa.study.product.application.dto.ProductCommand;
import msa.study.product.domain.service.ProductService;
import msa.study.product.domain.service.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static msa.study.product.infrastructure.factory.TestFactory.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductFacadeTest {

    @Autowired
    private ProductFacade productFacade;

    @MockBean
    private ProductService productService;

    @DisplayName("레이아웃 상품 등록")
    @Test
    void layoutProductRegister() {

        given(productService.layoutProductRegister(any(ProductCommand.LayoutProductRegister.class))).willReturn(layoutIdInfoDTOMono());

        Mono<ProductDTO.LayoutIdInfo> result = productFacade.layoutProductRegister(layoutProductRegisterCommand());

        verify(productService).layoutProductRegister(any(ProductCommand.LayoutProductRegister.class));

        StepVerifier.create(result.log())
            .assertNext(layoutIdInfo -> assertNotNull(layoutIdInfo.getLayoutId()))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 수정")
    @Test
    void layoutProductModify() {

        given(productService.layoutProductModify(any(ProductCommand.LayoutProductModify.class))).willReturn(Mono.empty());

        Mono<Void> result = productFacade.layoutProductModify(layoutProductModifyCommand());

        verify(productService).layoutProductModify(any(ProductCommand.LayoutProductModify.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 정보 조회")
    @Test
    void layoutProductInfo() {

        given(productService.layoutProductInfo(anyString())).willReturn(layoutProductInfoDTOMono());

        Mono<ProductDTO.LayoutProductInfo> result = productFacade.layoutProductInfo(UUID.randomUUID().toString());

        verify(productService).layoutProductInfo(anyString());

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 목록 조회")
    @Test
    void layoutProductList() {

        given(productService.layoutProductList()).willReturn(layoutProductListDTOMono());

        Mono<ProductDTO.LayoutProductList> result = productFacade.layoutProductList();

        verify(productService).layoutProductList();

        StepVerifier.create(result.log())
            .assertNext(layoutProductList -> assertNotNull(layoutProductList.getLayoutProductList()))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 삭제")
    @Test
    void layoutProductDelete() {

        given(productService.layoutProductDelete(anyString())).willReturn(Mono.empty());

        Mono<Void> result = productFacade.layoutProductDelete(UUID.randomUUID().toString());

        verify(productService).layoutProductDelete(anyString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}
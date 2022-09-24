package msa.study.product.domain.service;

import msa.study.product.domain.Layout;
import msa.study.product.domain.service.dto.ProductDTO;
import msa.study.product.domain.service.dto.ProductDTOMapper;
import msa.study.product.infrastructure.dao.LayoutRepository;
import msa.study.product.infrastructure.dao.ProductRepository;
import msa.study.product.infrastructure.exception.status.AlreadyDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static msa.study.product.infrastructure.factory.TestFactory.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductReaderTest {

    @Autowired
    private ProductReader productReader;

    @MockBean
    private LayoutRepository layoutRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductDTOMapper productDTOMapper;

    @DisplayName("이미 존재하는 레이아웃인지 확인")
    @Test
    void layoutExistCheck() {

        given(layoutRepository.findByName(anyString())).willReturn(Mono.empty());

        Mono<Void> result = productReader.layoutExistCheck(getLayoutName());

        verify(layoutRepository).findByName(anyString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("이미 존재하는 레이아웃인지 확인 > 이미 존재함")
    @Test
    void layoutExistCheckException() {

        given(layoutRepository.findByName(anyString())).willReturn(layoutMono());

        Mono<Void> result = productReader.layoutExistCheck(getLayoutName());

        verify(layoutRepository).findByName(anyString());

        StepVerifier.create(result.log())
            .expectError(AlreadyDataException.class)
            .verify();
    }

    @DisplayName("레이아웃 상품 정보 조회")
    @Test
    void findLayoutProductAggregate() {

        given(layoutRepository.findById(anyString())).willReturn(layoutMono());
        given(productRepository.findAllByLayoutId(anyString())).willReturn(productFlux());

        Mono<ProductDTO.LayoutProductAggregate> result = productReader.findLayoutProductAggregate(getLayoutName());

        verify(layoutRepository).findById(anyString());
        verify(productRepository).findAllByLayoutId(anyString());

        StepVerifier.create(result.log())
            .assertNext(layoutProductAggregate -> assertNotNull(layoutProductAggregate.getLayout()))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 목록 조회")
    @Test
    void findAllLayoutProductAggregate() {

        given(layoutRepository.findAll()).willReturn(layoutFlux());
        given(productRepository.findAllByLayoutId(anyString())).willReturn(productFlux());
        given(productDTOMapper.of(any(Layout.class), anyList())).willReturn(layoutProductInfoDTO());

        Mono<ProductDTO.LayoutProductList> result = productReader.findAllLayoutProduct();

        verify(layoutRepository).findAll();

        StepVerifier.create(result.log())
            .assertNext(layoutProductList -> assertNotNull(layoutProductList.getLayoutProductList()))
            .verifyComplete();
    }
}
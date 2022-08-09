package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.infrastructure.dao.LayoutRepository;
import dreamus.assignment.product.infrastructure.dao.ProductRepository;
import dreamus.assignment.product.infrastructure.exception.status.AlreadyDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
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

    @DisplayName("레이아웃 상품 조회")
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
}
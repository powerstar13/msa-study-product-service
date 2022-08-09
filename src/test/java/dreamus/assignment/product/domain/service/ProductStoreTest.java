package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.Product;
import dreamus.assignment.product.infrastructure.dao.LayoutRepository;
import dreamus.assignment.product.infrastructure.dao.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductStoreTest {

    @Autowired
    private ProductStore productStore;

    @MockBean
    private LayoutRepository layoutRepository;
    @MockBean
    private ProductRepository productRepository;

    @DisplayName("레이아웃 상품 등록")
    @Test
    void layoutProductRegister() {
        ProductCommand.LayoutProductRegister command = layoutProductRegisterCommand();

        given(layoutRepository.save(any(Layout.class))).willReturn(layoutMono());
        given(productRepository.saveAll(anyList())).willReturn(productFlux());

        Mono<Layout> result = productStore.layoutProductRegister(command);

        verify(layoutRepository).save(any(Layout.class));

        StepVerifier.create(result.log())
            .assertNext(layout -> assertEquals(command.getName(), layout.getName()))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 수정")
    @Test
    void layoutProductModify() {

        given(layoutRepository.save(any(Layout.class))).willReturn(layoutMono());
        given(productRepository.delete(any(Product.class))).willReturn(Mono.empty());
        given(productRepository.saveAll(anyList())).willReturn(productFlux());

        Mono<Void> result = productStore.layoutProductModify(layoutProductAggregateDTO(), layoutProductModifyCommand());

        verify(layoutRepository).save(any(Layout.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}
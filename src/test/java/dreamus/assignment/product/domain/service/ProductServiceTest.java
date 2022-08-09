package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutMono;
import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutProductRegisterCommand;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @DisplayName("레이아웃 상품 등록")
    @Test
    void layoutProductRegister() {

        given(productReader.layoutExistCheck(anyString())).willReturn(Mono.empty());
        given(productStore.layoutProductRegister(any(ProductCommand.LayoutProductRegister.class))).willReturn(layoutMono());

        Mono<ProductDTO.LayoutIdInfo> result = productService.layoutProductRegister(layoutProductRegisterCommand());

        verify(productReader).layoutExistCheck(anyString());
        verify(productStore).layoutProductRegister(any(ProductCommand.LayoutProductRegister.class));

        StepVerifier.create(result.log())
            .assertNext(layoutIdInfo -> assertNotNull(layoutIdInfo.getLayoutId()))
            .verifyComplete();
    }
}
package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.domain.service.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.getLayoutName;
import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutMono;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @DisplayName("레이아웃 등록")
    @Test
    void layoutRegister() {

        String name = getLayoutName();

        given(productReader.layoutExistCheck(anyString())).willReturn(Mono.empty());
        given(productStore.layoutRegister(anyString())).willReturn(layoutMono());

        Mono<ProductDTO.LayoutIdInfo> result = productService.layoutRegister(name);

        verify(productReader).layoutExistCheck(anyString());
        verify(productStore).layoutRegister(anyString());

        StepVerifier.create(result.log())
            .assertNext(layoutIdInfo -> assertNotNull(layoutIdInfo.getLayoutId()))
            .verifyComplete();
    }
}
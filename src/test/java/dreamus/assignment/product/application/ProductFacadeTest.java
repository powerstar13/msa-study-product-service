package dreamus.assignment.product.application;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.service.ProductService;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutIdInfoDTOMono;
import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutProductRegisterCommand;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
}
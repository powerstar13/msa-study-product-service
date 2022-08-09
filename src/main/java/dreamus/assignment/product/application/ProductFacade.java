package dreamus.assignment.product.application;

import dreamus.assignment.product.domain.service.ProductService;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;

    /**
     * 레이아웃 등록
     * @param name: 레이아웃 이름
     * @return LayoutIdInfo: 레이아웃 식별키
     */
    public Mono<ProductDTO.LayoutIdInfo> layoutRegister(String name) {
        return productService.layoutRegister(name);
    }
}

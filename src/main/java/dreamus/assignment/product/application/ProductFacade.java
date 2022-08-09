package dreamus.assignment.product.application;

import dreamus.assignment.product.application.dto.ProductCommand;
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
     * 레이아웃 상품 등록
     * @param command: 레이아웃 상품 정보
     * @return LayoutIdInfo: 레이아웃 식별키
     */
    public Mono<ProductDTO.LayoutIdInfo> layoutProductRegister(ProductCommand.LayoutProductRegister command) {
        return productService.layoutProductRegister(command);
    }

    /**
     * 레이아웃 상품 수정
     * @param command: 레이아웃 상품 정보
     */
    public Mono<Void> layoutProductModify(ProductCommand.LayoutProductModify command) {
        return productService.layoutProductModify(command);
    }

    /**
     * 레이아웃 상품 정보 조회
     * @param layoutId: 레이아웃 식별키
     * @return LayoutProductInfo: 레이아웃 상품 정보
     */
    public Mono<ProductDTO.LayoutProductInfo> layoutProductInfo(String layoutId) {
        return productService.layoutProductInfo(layoutId);
    }
}

package msa.study.product.application;

import msa.study.product.application.dto.ProductCommand;
import msa.study.product.domain.service.ProductService;
import msa.study.product.domain.service.dto.ProductDTO;
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

    /**
     * 레이아웃 상품 목록 조회
     * @return LayoutProductList: 레이아웃 상품 목록
     */
    public Mono<ProductDTO.LayoutProductList> layoutProductList() {
        return productService.layoutProductList();
    }

    /**
     * 레이아웃 상품 삭제
     * @param layoutId: 레이아웃 식별키
     */
    public Mono<Void> layoutProductDelete(String layoutId) {
        return productService.layoutProductDelete(layoutId);
    }
}

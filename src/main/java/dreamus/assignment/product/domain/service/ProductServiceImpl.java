package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.domain.service.dto.ProductDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductReader productReader;
    private final ProductStore productStore;
    private final ProductDTOMapper productDTOMapper;

    /**
     * 레이아웃 상품 등록
     * @param command: 레이아웃 상품 정보
     * @return 레이아웃 식별키
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<ProductDTO.LayoutIdInfo> layoutProductRegister(ProductCommand.LayoutProductRegister command) {

        return productReader.layoutExistCheck(command.getName()) // 1. 이미 등록된 레이아웃이 있는지 확인
            .then(productStore.layoutProductRegister(command) // 2. 레이아웃 상품 등록
                .flatMap(layout -> Mono.just(new ProductDTO.LayoutIdInfo(layout.getLayoutId())))
            );
    }

    /**
     * 레이아웃 상품 수정
     * @param command: 레이아웃 상품 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> layoutProductModify(ProductCommand.LayoutProductModify command) {

        return productReader.findLayoutProductAggregate(command.getLayoutId()) // 1. 레이아웃 상품 정보 조회
            .flatMap(layoutProductAggregate -> productStore.layoutProductModify(layoutProductAggregate, command)); // 2. 레이아웃 상품 수정
    }

    /**
     * 레이아웃 상품 정보 조회
     * @param layoutId: 레이아웃 식별키
     * @return LayoutProductInfo: 레이아웃 상품 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<ProductDTO.LayoutProductInfo> layoutProductInfo(String layoutId) {

        return productReader.findLayoutProductAggregate(layoutId) // 레이아웃 상품 정보 조회
            .flatMap(layoutProductAggregate -> Mono.just(productDTOMapper.of(layoutProductAggregate.getLayout(), layoutProductAggregate.getProductList())));
    }

    /**
     * 레이아웃 상품 목록 조회
     * @return LayoutProductList: 레이아웃 상품 목록
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<ProductDTO.LayoutProductList> layoutProductList() {
        return productReader.findAllLayoutProduct(); // 레이아웃 상품 목록 조회
    }

    /**
     * 레이아웃 상품 삭제
     * @param layoutId: 레이아웃 식별키
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> layoutProductDelete(String layoutId) {

        return productReader.findLayoutProductAggregate(layoutId) // 1. 레이아웃 상품 정보 조회
            .flatMap(productStore::layoutProductDelete); // 2. 레이아웃 상품 삭제
    }
}

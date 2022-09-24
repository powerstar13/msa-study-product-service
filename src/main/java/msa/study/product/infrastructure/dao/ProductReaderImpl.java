package msa.study.product.infrastructure.dao;

import msa.study.product.domain.service.ProductReader;
import msa.study.product.domain.service.dto.ProductDTO;
import msa.study.product.domain.service.dto.ProductDTOMapper;
import msa.study.product.infrastructure.exception.status.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductReaderImpl implements ProductReader {

    private final LayoutRepository layoutRepository;
    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;

    /**
     * 이미 존재하는 레이아웃인지 확인
     * @param name: 레이아웃 이름
     */
    @Override
    public Mono<Void> layoutExistCheck(String name) {

        return layoutRepository.findByName(name)
            .hasElement()
            .flatMap(aBoolean -> {
                if (aBoolean) return Mono.error(ExceptionMessage.AlreadyDataLayout.getException());

                return Mono.empty();
            });
    }

    /**
     * 레이아웃 상품 정보 조회
     * @param layoutId: 레이아웃 식별키
     * @return LayoutProductAggregate: 레이아웃 상품 애그리거트
     */
    @Override
    public Mono<ProductDTO.LayoutProductAggregate> findLayoutProductAggregate(String layoutId) {

        return layoutRepository.findById(layoutId) // 1. 레이아웃 정보 조회
            .switchIfEmpty(Mono.error(ExceptionMessage.NotFoundLayout.getException()))
            .zipWith(productRepository.findAllByLayoutId(layoutId).collectList()) // 2. 상품 목록 조회
            .map(objects ->
                new ProductDTO.LayoutProductAggregate(objects.getT1(), objects.getT2())
            );
    }

    /**
     * 레이아웃 상품 목록 조회
     * @return LayoutProductList: 레이아웃 상품 목록
     */
    @Override
    public Mono<ProductDTO.LayoutProductList> findAllLayoutProduct() {

        return layoutRepository.findAll() // 1. 레이아웃 전체 조회
            .flatMap(layout ->
                productRepository.findAllByLayoutId(layout.getLayoutId()) // 2. 레이아웃별 상품 목록 조회
                    .collectList()
                    .map(productList -> productDTOMapper.of(layout, productList))
            )
            .collectList()
            .map(ProductDTO.LayoutProductList::new);
    }
}

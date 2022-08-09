package dreamus.assignment.product.infrastructure.dao;

import dreamus.assignment.product.domain.service.ProductReader;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.infrastructure.exception.status.AlreadyDataException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.infrastructure.exception.status.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductReaderImpl implements ProductReader {

    private final LayoutRepository layoutRepository;
    private final ProductRepository productRepository;

    /**
     * 이미 존재하는 레이아웃인지 확인
     * @param name: 레이아웃 이름
     */
    @Override
    public Mono<Void> layoutExistCheck(String name) {

        return layoutRepository.findByName(name)
            .hasElement()
            .flatMap(aBoolean -> {
                if (aBoolean) return Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataLayout.getMessage()));

                return Mono.empty();
            });
    }

    /**
     * 레이아웃 상품 조회
     * @param layoutId: 레이아웃 식별키
     * @return LayoutProductAggregate: 레이아웃 상품 애그리거트
     */
    @Override
    public Mono<ProductDTO.LayoutProductAggregate> findLayoutProductAggregate(String layoutId) {

        return layoutRepository.findById(layoutId)
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundLayout.getMessage())))
            .zipWith(productRepository.findAllByLayoutId(layoutId).collectList())
            .flatMap(objects ->
                Mono.just(new ProductDTO.LayoutProductAggregate(objects.getT1(), objects.getT2()))
            );
    }
}

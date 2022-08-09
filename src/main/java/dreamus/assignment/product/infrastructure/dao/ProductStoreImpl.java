package dreamus.assignment.product.infrastructure.dao;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.Product;
import dreamus.assignment.product.domain.service.ProductStore;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.infrastructure.exception.status.RegisterFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductStoreImpl implements ProductStore {

    private final LayoutRepository layoutRepository;
    private final ProductRepository productRepository;

    /**
     * 레이아웃 상품 등록
     * @param command: 레이아웃 상품 정보
     * @return Layout: 레이아웃 레퍼런스
     */
    @Override
    public Mono<Layout> layoutProductRegister(ProductCommand.LayoutProductRegister command) {

        return layoutRepository.save(command.toEntity())
            .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailLayout.getMessage())))
            .flatMap(layout -> {
                List<Product> productList = command.getProductList().stream()
                    .map(productRegister -> productRegister.toEntity(layout.getLayoutId()))
                    .collect(Collectors.toList());

                return productRepository.saveAll(productList)
                    .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailProduct.getMessage())))
                    .then(Mono.just(layout));
            });
    }
}

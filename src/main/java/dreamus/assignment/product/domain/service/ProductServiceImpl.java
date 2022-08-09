package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductReader productReader;
    private final ProductStore productStore;

    /**
     * 레이아웃 상품 등록
     * @param command: 레이아웃 상품 정보
     * @return 레이아웃 식별키
     */
    @Override
    public Mono<ProductDTO.LayoutIdInfo> layoutProductRegister(ProductCommand.LayoutProductRegister command) {

        return productReader.layoutExistCheck(command.getName())
            .then(productStore.layoutProductRegister(command)
                .flatMap(layout -> Mono.just(new ProductDTO.LayoutIdInfo(layout.getLayoutId())))
            );
    }
}

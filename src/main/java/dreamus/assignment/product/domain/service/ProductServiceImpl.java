package dreamus.assignment.product.domain.service;

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
     * 레이아웃 등록
     * @param name: 레이아웃 이름
     * @return 레이아웃 식별키
     */
    @Override
    public Mono<ProductDTO.LayoutIdInfo> layoutRegister(String name) {

        return productReader.layoutExistCheck(name)
            .then(productStore.layoutRegister(name)
                .flatMap(layout -> Mono.just(new ProductDTO.LayoutIdInfo(layout.getLayoutId())))
            );
    }
}

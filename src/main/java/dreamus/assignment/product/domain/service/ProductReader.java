package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.domain.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductReader {

    Mono<Void> layoutExistCheck(String layoutName);

    Mono<ProductDTO.LayoutProductAggregate> findLayoutProductAggregate(String layoutId);

    Mono<ProductDTO.LayoutProductList> findAllLayoutProduct();
}

package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.domain.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDTO.LayoutIdInfo> layoutRegister(String name);
}

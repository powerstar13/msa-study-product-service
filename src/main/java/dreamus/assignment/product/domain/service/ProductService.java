package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDTO.LayoutIdInfo> layoutProductRegister(ProductCommand.LayoutProductRegister command);

    Mono<Void> layoutProductModify(ProductCommand.LayoutProductModify command);
}

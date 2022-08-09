package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductStore {

    Mono<Layout> layoutProductRegister(ProductCommand.LayoutProductRegister command);

    Mono<Void> layoutProductModify(ProductDTO.LayoutProductAggregate aggregate, ProductCommand.LayoutProductModify command);
}

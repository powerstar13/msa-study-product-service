package msa.study.product.domain.service;

import msa.study.product.application.dto.ProductCommand;
import msa.study.product.domain.Layout;
import msa.study.product.domain.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductStore {

    Mono<Layout> layoutProductRegister(ProductCommand.LayoutProductRegister command);

    Mono<Void> layoutProductModify(ProductDTO.LayoutProductAggregate aggregate, ProductCommand.LayoutProductModify command);

    Mono<Void> layoutProductDelete(ProductDTO.LayoutProductAggregate aggregate);
}

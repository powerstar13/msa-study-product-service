package msa.study.product.domain.service;

import msa.study.product.domain.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductReader {

    Mono<Void> layoutExistCheck(String layoutName);

    Mono<ProductDTO.LayoutProductAggregate> findLayoutProductAggregate(String layoutId);

    Mono<ProductDTO.LayoutProductList> findAllLayoutProduct();
}

package msa.study.product.domain.service;

import msa.study.product.application.dto.ProductCommand;
import msa.study.product.domain.service.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDTO.LayoutIdInfo> layoutProductRegister(ProductCommand.LayoutProductRegister command);

    Mono<Void> layoutProductModify(ProductCommand.LayoutProductModify command);

    Mono<ProductDTO.LayoutProductInfo> layoutProductInfo(String layoutId);

    Mono<ProductDTO.LayoutProductList> layoutProductList();

    Mono<Void> layoutProductDelete(String layoutId);
}

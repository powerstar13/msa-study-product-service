package dreamus.assignment.product.domain.service;

import reactor.core.publisher.Mono;

public interface ProductReader {

    Mono<Void> layoutExistCheck(String layoutName);
}

package dreamus.assignment.product.domain.service;

import reactor.core.publisher.Mono;

public interface LayoutReader {

    Mono<Void> layoutExistCheck(String layoutName);
}

package dreamus.assignment.product.infrastructure.dao;

import dreamus.assignment.product.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    Flux<Product> findAllByLayoutId(String layoutId);
}

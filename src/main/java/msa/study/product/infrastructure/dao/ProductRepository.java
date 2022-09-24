package msa.study.product.infrastructure.dao;

import msa.study.product.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    Flux<Product> findAllByLayoutId(String layoutId);
}

package msa.study.product.infrastructure.dao;

import msa.study.product.domain.Layout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LayoutRepository extends ReactiveCrudRepository<Layout, String> {

    Mono<Layout> findByName(String name);
}

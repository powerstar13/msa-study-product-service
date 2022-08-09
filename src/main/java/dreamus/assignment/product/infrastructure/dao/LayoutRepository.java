package dreamus.assignment.product.infrastructure.dao;

import dreamus.assignment.product.domain.Layout;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayoutRepository extends ReactiveCrudRepository<Layout, String> {
}

package dreamus.assignment.product.infrastructure.dao;

import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.service.ProductStore;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.infrastructure.exception.status.RegisterFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductStoreImpl implements ProductStore {

    private final LayoutRepository layoutRepository;

    /**
     * 레이아웃 등록
     * @param name: 레이아웃 이름
     */
    @Override
    public Mono<Layout> layoutRegister(String name) {

        Layout layout = Layout.builder().name(name).build();

        return layoutRepository.save(layout)
            .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailLayout.getMessage())));
    }
}

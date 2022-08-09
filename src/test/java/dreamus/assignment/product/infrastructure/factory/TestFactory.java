package dreamus.assignment.product.infrastructure.factory;

import dreamus.assignment.product.domain.Layout;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestFactory {

    /**
     * 레이아웃 정보
     */
    public static Layout layout() {

        return Layout.builder()
            .layoutId(UUID.randomUUID().toString())
            .name("레이아웃 이름")
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Mono<Layout> layoutMono() {
        return Mono.just(layout());
    }
}

package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.infrastructure.dao.LayoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.getLayoutName;
import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutMono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductStoreTest {

    @Autowired
    private ProductStore productStore;

    @MockBean
    private LayoutRepository layoutRepository;

    @DisplayName("레이아웃 등록")
    @Test
    void layoutRegister() {

        String name = getLayoutName();

        given(layoutRepository.save(any(Layout.class))).willReturn(layoutMono());

        Mono<Layout> result = productStore.layoutRegister(name);

        verify(layoutRepository).save(any(Layout.class));

        StepVerifier.create(result.log())
            .assertNext(layout -> assertEquals(name, layout.getName()))
            .verifyComplete();
    }
}
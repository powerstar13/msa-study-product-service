package dreamus.assignment.product.domain.service;

import dreamus.assignment.product.infrastructure.dao.LayoutRepository;
import dreamus.assignment.product.infrastructure.exception.status.AlreadyDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutMono;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LayoutReaderTest {

    @Autowired
    private LayoutReader layoutReader;

    @MockBean
    private LayoutRepository layoutRepository;

    @DisplayName("이미 존재하는 레이아웃인지 확인")
    @Test
    void layoutExistCheck() {

        given(layoutRepository.findByName(anyString())).willReturn(Mono.empty());

        Mono<Void> result = layoutReader.layoutExistCheck("레이아웃 이름");

        verify(layoutRepository).findByName(anyString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("이미 존재하는 레이아웃인지 확인 > 이미 존재함")
    @Test
    void layoutExistCheckException() {

        given(layoutRepository.findByName(anyString())).willReturn(layoutMono());

        Mono<Void> result = layoutReader.layoutExistCheck("레이아웃 이름");

        verify(layoutRepository).findByName(anyString());

        StepVerifier.create(result.log())
            .expectError(AlreadyDataException.class)
            .verify();
    }
}
package dreamus.assignment.product.infrastructure.dao;

import dreamus.assignment.product.domain.service.LayoutReader;
import dreamus.assignment.product.infrastructure.exception.status.AlreadyDataException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LayoutReaderImpl implements LayoutReader {

    private final LayoutRepository layoutRepository;

    /**
     * 이미 존재하는 레이아웃인지 확인
     * @param name: 레이아웃 이름
     */
    @Override
    public Mono<Void> layoutExistCheck(String name) {

        return layoutRepository.findByName(name)
            .hasElement()
            .flatMap(aBoolean -> {
                if (aBoolean) return Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataLayout.getMessage()));

                return Mono.empty();
            });
    }
}

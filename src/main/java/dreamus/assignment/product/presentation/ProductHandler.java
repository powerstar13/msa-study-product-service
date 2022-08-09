package dreamus.assignment.product.presentation;

import dreamus.assignment.product.application.ProductFacade;
import dreamus.assignment.product.infrastructure.exception.status.BadRequestException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.presentation.request.LayoutRegisterRequest;
import dreamus.assignment.product.presentation.response.LayoutRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductFacade productFacade;

    /**
     * 레이아웃 등록
     * @param serverRequest: 등록할 레이아웃 정보
     * @return LayoutRegisterResponse: 레이아웃 식별키
     */
    @NotNull
    public Mono<ServerResponse> layoutRegister(ServerRequest serverRequest) {

        Mono<LayoutRegisterResponse> response = serverRequest.bodyToMono(LayoutRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return productFacade.layoutRegister(request.getName());
            })
            .flatMap(layoutIdInfo -> Mono.just(new LayoutRegisterResponse(layoutIdInfo.getLayoutId())));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, LayoutRegisterResponse.class);
    }
}

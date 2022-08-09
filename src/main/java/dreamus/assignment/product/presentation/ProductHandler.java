package dreamus.assignment.product.presentation;

import dreamus.assignment.product.application.ProductFacade;
import dreamus.assignment.product.infrastructure.exception.status.BadRequestException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.presentation.request.LayoutProductModifyRequest;
import dreamus.assignment.product.presentation.request.LayoutProductRegisterRequest;
import dreamus.assignment.product.presentation.request.ProductRequestMapper;
import dreamus.assignment.product.presentation.response.LayoutProductRegisterResponse;
import dreamus.assignment.product.presentation.shared.response.SuccessResponse;
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
    private final ProductRequestMapper productRequestMapper;

    /**
     * 레이아웃 상품 등록
     * @param serverRequest: 등록할 레이아웃 상품 정보
     * @return ServerResponse: 레이아웃 식별키
     */
    @NotNull
    public Mono<ServerResponse> layoutProductRegister(ServerRequest serverRequest) {

        Mono<LayoutProductRegisterResponse> response = serverRequest.bodyToMono(LayoutProductRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return productFacade.layoutProductRegister(productRequestMapper.of(request));
            })
            .flatMap(layoutIdInfo -> Mono.just(new LayoutProductRegisterResponse(layoutIdInfo.getLayoutId())));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, LayoutProductRegisterResponse.class);
    }

    /**
     * 레이아웃 상품 수정
     * @param serverRequest: 수정할 레이아웃 상품 정보
     * @return ServerResponse: 처리 완료
     */
    @NotNull
    public Mono<ServerResponse> layoutProductModify(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(LayoutProductModifyRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return productFacade.layoutProductModify(productRequestMapper.of(request))
                    .then(
                        ok().contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(new SuccessResponse()), SuccessResponse.class)
                    );
            });
    }
}

package dreamus.assignment.product.presentation;

import dreamus.assignment.product.application.ProductFacade;
import dreamus.assignment.product.infrastructure.exception.status.BadRequestException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.presentation.request.LayoutProductModifyRequest;
import dreamus.assignment.product.presentation.request.LayoutProductRegisterRequest;
import dreamus.assignment.product.presentation.request.ProductRequestMapper;
import dreamus.assignment.product.presentation.response.LayoutProductRegisterResponse;
import dreamus.assignment.product.presentation.response.ProductResponseMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static dreamus.assignment.product.presentation.shared.response.ServerResponseFactory.successBodyValue;
import static dreamus.assignment.product.presentation.shared.response.ServerResponseFactory.successOnly;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductFacade productFacade;
    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;

    /**
     * 레이아웃 상품 등록
     * @param serverRequest: 등록할 레이아웃 상품 정보
     * @return ServerResponse: 레이아웃 식별키
     */
    @NotNull
    public Mono<ServerResponse> layoutProductRegister(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(LayoutProductRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return productFacade.layoutProductRegister(productRequestMapper.of(request));
            })
            .flatMap(response -> successBodyValue(new LayoutProductRegisterResponse(response.getLayoutId())));
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
                    .then(successOnly());
            });
    }

    /**
     * 레이아웃 상품 정보 조회
     * @param serverRequest: 레이아웃 식별키
     * @return ServerResponse: 레이아웃 상품 정보
     */
    @NotNull
    public Mono<ServerResponse> layoutProductInfo(ServerRequest serverRequest) {

        String layoutId = serverRequest.pathVariable("layoutId"); // 레이아웃 식별키 추출
        if (StringUtils.isBlank(layoutId)) throw new BadRequestException(ExceptionMessage.IsRequiredLayoutId.getMessage());

        return productFacade.layoutProductInfo(layoutId)
            .flatMap(response -> successBodyValue(productResponseMapper.of(response)));
    }

    /**
     * 레이아웃 상품 목록 조회
     * @return ServerResponse: 레이아웃 상품 목록
     */
    @NotNull
    public Mono<ServerResponse> layoutProductList(ServerRequest serverRequest) {

        return productFacade.layoutProductList()
            .flatMap(response -> successBodyValue(productResponseMapper.of(response)));
    }

    /**
     * 레이아웃 상품 삭제
     * @param serverRequest: 레이아웃 식별키
     * @return ServerResponse: 처리 완료
     */
    @NotNull
    public Mono<ServerResponse> layoutProductDelete(ServerRequest serverRequest) {

        String layoutId = serverRequest.pathVariable("layoutId"); // 레이아웃 식별키 추출
        if (StringUtils.isBlank(layoutId)) throw new BadRequestException(ExceptionMessage.IsRequiredLayoutId.getMessage());

        return productFacade.layoutProductDelete(layoutId)
            .then(successOnly());
    }
}

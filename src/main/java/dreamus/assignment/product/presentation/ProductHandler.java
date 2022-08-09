package dreamus.assignment.product.presentation;

import dreamus.assignment.product.application.ProductFacade;
import dreamus.assignment.product.infrastructure.exception.status.BadRequestException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.presentation.request.LayoutProductModifyRequest;
import dreamus.assignment.product.presentation.request.LayoutProductRegisterRequest;
import dreamus.assignment.product.presentation.request.ProductRequestMapper;
import dreamus.assignment.product.presentation.response.LayoutProductInfoResponse;
import dreamus.assignment.product.presentation.response.LayoutProductListResponse;
import dreamus.assignment.product.presentation.response.LayoutProductRegisterResponse;
import dreamus.assignment.product.presentation.response.ProductResponseMapper;
import dreamus.assignment.product.presentation.shared.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    private final ProductResponseMapper productResponseMapper;

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

    /**
     * 레이아웃 상품 정보 조회
     * @param serverRequest: 레이아웃 식별키
     * @return ServerResponse: 레이아웃 상품 정보
     */
    @NotNull
    public Mono<ServerResponse> layoutProductInfo(ServerRequest serverRequest) {

        String layoutId = serverRequest.pathVariable("layoutId"); // 레이아웃 식별키 추출
        if (StringUtils.isBlank(layoutId)) throw new BadRequestException(ExceptionMessage.IsRequiredLayoutId.getMessage());

        Mono<LayoutProductInfoResponse> response = productFacade.layoutProductInfo(layoutId)
            .flatMap(layoutProductInfo -> Mono.just(productResponseMapper.of(layoutProductInfo)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, LayoutProductInfoResponse.class);
    }

    /**
     * 레이아웃 상품 목록 조회
     * @return ServerResponse: 레이아웃 상품 목록
     */
    @NotNull
    public Mono<ServerResponse> layoutProductList(ServerRequest serverRequest) {

        Mono<LayoutProductListResponse> response = productFacade.layoutProductList()
            .flatMap(layoutProductList -> Mono.just(productResponseMapper.of(layoutProductList)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, LayoutProductListResponse.class);
    }
}

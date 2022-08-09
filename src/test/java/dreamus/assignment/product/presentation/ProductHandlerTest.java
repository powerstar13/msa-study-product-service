package dreamus.assignment.product.presentation;

import dreamus.assignment.product.application.ProductFacade;
import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.infrastructure.router.RouterPathPattern;
import dreamus.assignment.product.presentation.request.LayoutProductModifyRequest;
import dreamus.assignment.product.presentation.request.LayoutProductRegisterRequest;
import dreamus.assignment.product.presentation.request.ProductRequestMapper;
import dreamus.assignment.product.presentation.response.LayoutProductRegisterResponse;
import dreamus.assignment.product.presentation.shared.WebFluxSharedHandlerTest;
import dreamus.assignment.product.presentation.shared.response.SuccessResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.*;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentFormat.isRequiredForModifyFormat;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(ProductHandler.class)
class ProductHandlerTest extends WebFluxSharedHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ProductFacade productFacade;
    @MockBean
    private ProductRequestMapper productRequestMapper;

    @DisplayName("레이아웃 상품 등록")
    @Test
    void layoutProductRegister() {
        // given
        given(productRequestMapper.of(any(LayoutProductRegisterRequest.class))).willReturn(layoutProductRegisterCommand());
        given(productFacade.layoutProductRegister(any(ProductCommand.LayoutProductRegister.class))).willReturn(layoutIdInfoDTOMono());

        // when
        final String URI = RouterPathPattern.LAYOUT_PRODUCT_REGISTER.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .post()
            .uri(URI)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(layoutProductRegisterRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("레이아웃 이름"),
                    fieldWithPath("productList[]").type(JsonFieldType.ARRAY).description("상품 목록"),
                    fieldWithPath("productList[].name").type(JsonFieldType.STRING).description("상품명"),
                    fieldWithPath("productList[].price").type(JsonFieldType.NUMBER).description("금액")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("layoutId").type(JsonFieldType.STRING).description("레이아웃 식별키")
                )
            ));

        FluxExchangeResult<LayoutProductRegisterResponse> flux = result.returnResult(LayoutProductRegisterResponse.class);

        // then
        verify(productRequestMapper).of(any(LayoutProductRegisterRequest.class));
        verify(productFacade).layoutProductRegister(any(ProductCommand.LayoutProductRegister.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.CREATED.value(), response.getRt());
                assertNotNull(response.getLayoutId());
            }))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 수정")
    @Test
    void layoutProductModify() {
        // given
        given(productRequestMapper.of(any(LayoutProductModifyRequest.class))).willReturn(layoutProductModifyCommand());
        given(productFacade.layoutProductModify(any(ProductCommand.LayoutProductModify.class))).willReturn(Mono.empty());

        // when
        final String URI = RouterPathPattern.LAYOUT_PRODUCT_MODIFY.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .put()
            .uri(URI)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(layoutProductModifyRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("layoutId").type(JsonFieldType.STRING).description("레이아웃 식별키"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("레이아웃 이름"),
                    fieldWithPath("productList[]").type(JsonFieldType.ARRAY).description("상품 목록"),
                    fieldWithPath("productList[].productId").type(JsonFieldType.STRING).description("상품 식별키").attributes(isRequiredForModifyFormat()).optional(),
                    fieldWithPath("productList[].name").type(JsonFieldType.STRING).description("상품명"),
                    fieldWithPath("productList[].price").type(JsonFieldType.NUMBER).description("금액")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지")
                )
            ));

        FluxExchangeResult<SuccessResponse> flux = result.returnResult(SuccessResponse.class);

        // then
        verify(productRequestMapper).of(any(LayoutProductModifyRequest.class));
        verify(productFacade).layoutProductModify(any(ProductCommand.LayoutProductModify.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }
}
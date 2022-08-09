package dreamus.assignment.product.presentation;

import dreamus.assignment.product.application.ProductFacade;
import dreamus.assignment.product.application.dto.ProductCommand;
import dreamus.assignment.product.domain.service.dto.ProductDTO;
import dreamus.assignment.product.infrastructure.router.RouterPathPattern;
import dreamus.assignment.product.presentation.request.LayoutProductModifyRequest;
import dreamus.assignment.product.presentation.request.LayoutProductRegisterRequest;
import dreamus.assignment.product.presentation.request.ProductRequestMapper;
import dreamus.assignment.product.presentation.response.LayoutProductInfoResponse;
import dreamus.assignment.product.presentation.response.LayoutProductListResponse;
import dreamus.assignment.product.presentation.response.LayoutProductRegisterResponse;
import dreamus.assignment.product.presentation.response.ProductResponseMapper;
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

import java.util.UUID;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.*;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentFormat.isRequiredForModifyFormat;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(ProductHandler.class)
class ProductHandlerTest extends WebFluxSharedHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ProductFacade productFacade;
    @MockBean
    private ProductRequestMapper productRequestMapper;
    @MockBean
    private ProductResponseMapper productResponseMapper;

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

    @DisplayName("레이아웃 상품 정보 조회")
    @Test
    void layoutProductInfo() {
        // given
        given(productFacade.layoutProductInfo(any(String.class))).willReturn(layoutProductInfoDTOMono());
        given(productResponseMapper.of(any(ProductDTO.LayoutProductInfo.class))).willReturn(layoutProductInfoResponse());

        // when
        final String URI = RouterPathPattern.LAYOUT_PRODUCT_INFO.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI, UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                pathParameters(
                    parameterWithName("layoutId").description("레이아웃 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("layoutId").type(JsonFieldType.STRING).description("레이아웃 식별키"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("레이아웃 이름"),
                    fieldWithPath("productList[]").type(JsonFieldType.ARRAY).description("상품 목록"),
                    fieldWithPath("productList[].productId").type(JsonFieldType.STRING).description("상품 식별키"),
                    fieldWithPath("productList[].name").type(JsonFieldType.STRING).description("상품명"),
                    fieldWithPath("productList[].price").type(JsonFieldType.NUMBER).description("금액")
                )
            ));

        FluxExchangeResult<LayoutProductInfoResponse> flux = result.returnResult(LayoutProductInfoResponse.class);

        // then
        verify(productFacade).layoutProductInfo(any(String.class));
        verify(productResponseMapper).of(any(ProductDTO.LayoutProductInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertNotNull(response);
            }))
            .verifyComplete();
    }

    @DisplayName("레이아웃 상품 목록 조회")
    @Test
    void layoutProductList() {
        // given
        given(productFacade.layoutProductList()).willReturn(layoutProductListDTOMono());
        given(productResponseMapper.of(any(ProductDTO.LayoutProductList.class))).willReturn(layoutProductListResponse());

        // when
        final String URI = RouterPathPattern.LAYOUT_PRODUCT_LIST.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI)
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("layoutProductList[]").type(JsonFieldType.ARRAY).description("레이아웃 상품 목록").optional(),
                    fieldWithPath("layoutProductList[].layoutId").type(JsonFieldType.STRING).description("레이아웃 식별키"),
                    fieldWithPath("layoutProductList[].name").type(JsonFieldType.STRING).description("레이아웃 이름"),
                    fieldWithPath("layoutProductList[].productList[]").type(JsonFieldType.ARRAY).description("상품 목록"),
                    fieldWithPath("layoutProductList[].productList[].productId").type(JsonFieldType.STRING).description("상품 식별키"),
                    fieldWithPath("layoutProductList[].productList[].name").type(JsonFieldType.STRING).description("상품명"),
                    fieldWithPath("layoutProductList[].productList[].price").type(JsonFieldType.NUMBER).description("금액")
                )
            ));

        FluxExchangeResult<LayoutProductListResponse> flux = result.returnResult(LayoutProductListResponse.class);

        // then
        verify(productFacade).layoutProductList();
        verify(productResponseMapper).of(any(ProductDTO.LayoutProductList.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertNotNull(response.getLayoutProductList());
            }))
            .verifyComplete();
    }
}
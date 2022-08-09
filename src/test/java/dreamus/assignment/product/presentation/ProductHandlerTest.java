package dreamus.assignment.product.presentation;

import dreamus.assignment.product.application.ProductFacade;
import dreamus.assignment.product.infrastructure.router.RouterPathPattern;
import dreamus.assignment.product.presentation.response.LayoutRegisterResponse;
import dreamus.assignment.product.presentation.shared.WebFluxSharedHandlerTest;
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
import reactor.test.StepVerifier;

import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutIdInfoDTOMono;
import static dreamus.assignment.product.infrastructure.factory.TestFactory.layoutRegisterRequest;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static dreamus.assignment.product.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

    @DisplayName("레이아웃 등록")
    @Test
    void layoutRegister() {
        // given
        given(productFacade.layoutRegister(anyString())).willReturn(layoutIdInfoDTOMono());

        // when
        final String URI = RouterPathPattern.LAYOUT_REGISTER.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .post()
            .uri(URI)
            .bodyValue(layoutRegisterRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("레이아웃 이름")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("layoutId").type(JsonFieldType.STRING).description("레이아웃 식별키")
                )
            ));

        FluxExchangeResult<LayoutRegisterResponse> flux = result.returnResult(LayoutRegisterResponse.class);

        // then
        verify(productFacade).layoutRegister(anyString());

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.CREATED.value(), response.getRt());
                assertNotNull(response.getLayoutId());
            }))
            .verifyComplete();
    }
}
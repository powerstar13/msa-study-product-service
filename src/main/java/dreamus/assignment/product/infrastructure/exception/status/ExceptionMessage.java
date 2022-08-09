package dreamus.assignment.product.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest("BadRequestException", "Request를 전달해주세요."),
    IsRequiredLayoutId("BadRequestException", "레이아웃 식별키를 전달해주세요."),
    IsRequiredLayoutName("BadRequestException", "레이아웃 이름을 입력해주세요."),
    IsRequiredProductInfo("BadRequestException", "상품 정보를 전달해주세요."),
    IsRequiredProductId("BadRequestException", "상품 식별키를 전달해주세요."),
    IsRequiredProductName("BadRequestException", "상품명을 입력해주세요."),
    IsRequiredProductPrice("BadRequestException", "상품 금액을 입력해주세요."),

    RegisterFailLayout("RegisterFailException", "레이아웃 등록에 실패했습니다. 관리자에게 문의 바랍니다."),
    RegisterFailProduct("RegisterFailException", "상품 등록에 실패했습니다. 관리자에게 문의 바랍니다."),

    AlreadyDataLayout("AlreadyDataException", "이미 등록된 레이아웃입니다."),
    AlreadyDataProduct("AlreadyDataException", "이미 등록된 상품입니다."),

    NotFoundLayout("NotFoundDataException", "조회된 레이아웃 정보가 없습니다."),
    NotFoundProduct("NotFoundDataException", "조회된 상품 정보가 없습니다."),

    ServerError("RuntimeException", "서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다.");

    private final String type;
    private final String message;
}

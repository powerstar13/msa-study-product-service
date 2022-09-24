package msa.study.product.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.server.ResponseStatusException;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest(new BadRequestException("Request를 전달해주세요.")),
    IsRequiredLayoutId(new BadRequestException("레이아웃 식별키를 전달해주세요.")),
    IsRequiredLayoutName(new BadRequestException("레이아웃 이름을 입력해주세요.")),
    IsRequiredProductInfo(new BadRequestException("상품 정보를 전달해주세요.")),
    IsRequiredProductId(new BadRequestException("상품 식별키를 전달해주세요.")),
    IsRequiredProductName(new BadRequestException("상품명을 입력해주세요.")),
    IsRequiredProductPrice(new BadRequestException("상품 금액을 입력해주세요.")),

    RegisterFailLayout(new RegisterFailException("레이아웃 등록에 실패했습니다. 관리자에게 문의 바랍니다.")),
    RegisterFailProduct(new RegisterFailException("상품 등록에 실패했습니다. 관리자에게 문의 바랍니다.")),

    AlreadyDataLayout(new AlreadyDataException("이미 등록된 레이아웃입니다.")),
    AlreadyDataProduct(new AlreadyDataException("이미 등록된 상품입니다.")),

    NotFoundLayout(new NotFoundDataException("조회된 레이아웃 정보가 없습니다.")),
    NotFoundProduct(new NotFoundDataException("조회된 상품 정보가 없습니다.")),

    ServerError(new ServerException("서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다."));

    private final ResponseStatusException exception;
}

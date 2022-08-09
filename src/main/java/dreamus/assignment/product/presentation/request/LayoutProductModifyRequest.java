package dreamus.assignment.product.presentation.request;

import dreamus.assignment.product.infrastructure.exception.status.BadRequestException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.presentation.request.dto.ProductModifyRequestDTO;
import dreamus.assignment.product.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayoutProductModifyRequest implements RequestVerify {

    private String layoutId; // 레이아웃 식별키
    private String name; // 레이아웃 이름
    private List<ProductModifyRequestDTO> productList; // 상품 목록

    @Override
    public void verify() {
        if (StringUtils.isBlank(layoutId)) throw new BadRequestException(ExceptionMessage.IsRequiredLayoutId.getMessage());
        if (StringUtils.isBlank(name)) throw new BadRequestException(ExceptionMessage.IsRequiredLayoutName.getMessage());
        if (productList == null)  throw new BadRequestException(ExceptionMessage.IsRequiredProductInfo.getMessage());
        productList.forEach(ProductModifyRequestDTO::verify); // 상품 정보 Request 유효성 검사
    }

    @Override
    public String toString() {
        return "{"
            + "\"layoutId\":\"" + layoutId + "\""
            + ", \"name\":\"" + name + "\""
            + ", \"productList\":" + productList
            + "}";
    }
}

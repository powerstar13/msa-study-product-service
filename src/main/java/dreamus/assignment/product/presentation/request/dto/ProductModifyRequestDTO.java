package dreamus.assignment.product.presentation.request.dto;

import dreamus.assignment.product.infrastructure.exception.status.BadRequestException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductModifyRequestDTO implements RequestVerify {

    private String productId; // 상품 식별키
    private String name; // 상풍명
    private Integer price; // 금액

    @Override
    public void verify() {
        if (StringUtils.isBlank(productId)) throw new BadRequestException(ExceptionMessage.IsRequiredProductId.getMessage());
        if (StringUtils.isBlank(name)) throw new BadRequestException(ExceptionMessage.IsRequiredProductName.getMessage());
        if (price == null || price < 0) throw new BadRequestException(ExceptionMessage.IsRequiredProductPrice.getMessage());
    }

    @Override
    public String toString() {
        return "{"
            + "\"productId\":\"" + productId + "\""
            + ", \"name\":\"" + name + "\""
            + ", \"price\":" + price
            + "}";
    }
}

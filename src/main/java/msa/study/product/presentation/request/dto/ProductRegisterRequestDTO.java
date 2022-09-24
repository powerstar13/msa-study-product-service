package msa.study.product.presentation.request.dto;

import msa.study.product.infrastructure.exception.status.ExceptionMessage;
import msa.study.product.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegisterRequestDTO implements RequestVerify {

    private String name; // 상풍명
    private Integer price; // 금액

    @Override
    public void verify() {
        if (StringUtils.isBlank(name)) throw ExceptionMessage.IsRequiredProductName.getException();
        if (price == null || price < 0) throw ExceptionMessage.IsRequiredProductPrice.getException();
    }

    @Override
    public String toString() {
        return "{"
            + "\"name\":\"" + name + "\""
            + ", \"price\":" + price
            + "}";
    }
}

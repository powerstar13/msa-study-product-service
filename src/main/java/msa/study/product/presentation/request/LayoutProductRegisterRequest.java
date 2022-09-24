package msa.study.product.presentation.request;

import msa.study.product.infrastructure.exception.status.ExceptionMessage;
import msa.study.product.presentation.request.dto.ProductRegisterRequestDTO;
import msa.study.product.presentation.shared.request.RequestVerify;
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
public class LayoutProductRegisterRequest implements RequestVerify {

    private String name; // 레이아웃 이름
    private List<ProductRegisterRequestDTO> productList; // 상품 목록

    @Override
    public void verify() {
        if (StringUtils.isBlank(name)) throw ExceptionMessage.IsRequiredLayoutName.getException();
        if (productList == null)  throw ExceptionMessage.IsRequiredProductInfo.getException();
        productList.forEach(ProductRegisterRequestDTO::verify); // 상품 정보 Request 유효성 검사
    }

    @Override
    public String toString() {
        return "{"
            + "\"name\":\"" + name + "\""
            + ", \"productList\":" + productList
            + "}";
    }
}

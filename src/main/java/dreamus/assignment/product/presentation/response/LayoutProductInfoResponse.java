package dreamus.assignment.product.presentation.response;

import dreamus.assignment.product.presentation.response.dto.LayoutProductResponseDTO;
import dreamus.assignment.product.presentation.shared.response.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayoutProductInfoResponse extends SuccessResponse {

    private String layoutId; // 레이아웃 식별키
    private String name; // 레이아웃 이름
    private List<LayoutProductResponseDTO.ProductInfo> productList; // 상품 목록

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"layoutId\":\"" + layoutId + "\""
            + ", \"name\":\"" + name + "\""
            + ", \"productList\":" + productList
            + "}";
    }
}

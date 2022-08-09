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
public class LayoutProductListResponse extends SuccessResponse {

    private List<LayoutProductResponseDTO.LayoutProductInfo> layoutProductList; // 레이아웃 상품 목록

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"layoutProductList\":" + layoutProductList
            + "}";
    }
}

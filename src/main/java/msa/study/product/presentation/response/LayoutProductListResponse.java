package msa.study.product.presentation.response;

import msa.study.product.presentation.response.dto.LayoutProductResponseDTO;
import msa.study.product.presentation.shared.response.SuccessResponse;
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

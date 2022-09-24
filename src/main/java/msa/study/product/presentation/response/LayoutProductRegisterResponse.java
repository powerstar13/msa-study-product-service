package msa.study.product.presentation.response;

import msa.study.product.presentation.shared.response.CreatedSuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayoutProductRegisterResponse extends CreatedSuccessResponse {

    private String layoutId; // 레이아웃 식별키

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"layoutId\":\"" + layoutId + "\""
            + "}";
    }
}

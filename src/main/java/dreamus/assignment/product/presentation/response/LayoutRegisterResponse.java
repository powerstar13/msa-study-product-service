package dreamus.assignment.product.presentation.response;

import dreamus.assignment.product.presentation.shared.response.CreatedSuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayoutRegisterResponse extends CreatedSuccessResponse {

    private String layoutId; // 레이아웃 식별키

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"layoutId\":\"" + layoutId + "\""
            + "}";
    }
}

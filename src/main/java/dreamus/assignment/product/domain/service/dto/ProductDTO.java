package dreamus.assignment.product.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ProductDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class LayoutIdInfo {

        private String layoutId; // 레이아웃 식별키

        @Override
        public String toString() {
            return "{"
                + "\"layoutId\":\"" + layoutId + "\""
                + "}";
        }
    }
}

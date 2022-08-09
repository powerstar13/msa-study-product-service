package dreamus.assignment.product.presentation.response.dto;

import lombok.Builder;
import lombok.Getter;

public class LayoutProductResponseDTO {

    @Getter
    @Builder
    public static class ProductInfo {

        private String productId; // 상품 식별키
        private String name; // 상풍명
        private Integer price; // 금액

        @Override
        public String toString() {
            return "{"
                + "\"productId\":\"" + productId + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"price\":" + price
                + "}";
        }
    }
}

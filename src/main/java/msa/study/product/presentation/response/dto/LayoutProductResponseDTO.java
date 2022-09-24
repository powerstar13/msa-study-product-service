package msa.study.product.presentation.response.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class LayoutProductResponseDTO {

    @Getter
    @Builder
    public static class LayoutProductInfo {

        private String layoutId; // 레이아웃 식별키
        private String name; // 레이아웃 이름
        private List<ProductInfo> productList; // 상품 목록

        @Override
        public String toString() {
            return "{"
                + "\"layoutId\":\"" + layoutId + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"productList\":" + productList
                + "}";
        }
    }

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

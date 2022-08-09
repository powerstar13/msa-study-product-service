package dreamus.assignment.product.domain.service.dto;

import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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

    @Getter
    @Builder
    @AllArgsConstructor
    public static class LayoutProductAggregate {

        private Layout layout; // 레이아웃 레퍼런스
        private List<Product> productList; // 상품 레퍼런스 목록

        @Override
        public String toString() {
            return "{"
                + "\"layout\":" + layout
                + ", \"productList\":" + productList
                + "}";
        }
    }
}

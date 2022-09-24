package msa.study.product.application.dto;

import msa.study.product.domain.Layout;
import msa.study.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ProductCommand {

    @Getter
    @Builder
    public static class LayoutProductRegister {

        private String name; // 레이아웃 이름
        private List<ProductRegister> productList; // 상품 목록

        public Layout toEntity() {
            return Layout.builder()
                .name(this.name)
                .build();
        }

        @Override
        public String toString() {
            return "{"
                + "\"name\":\"" + name + "\""
                + ", \"productList\":" + productList
                + "}";
        }
    }

    @Getter
    @Builder
    public static class ProductRegister {

        private String name; // 상풍명
        private int price; // 금액

        public Product toEntity(String layoutId) {
            return Product.builder()
                .name(this.name)
                .price(this.price)
                .layoutId(layoutId)
                .build();
        }

        @Override
        public String toString() {
            return "{"
                + "\"name\":\"" + name + "\""
                + ", \"price\":" + price
                + "}";
        }
    }

    @Getter
    @Builder
    public static class LayoutProductModify {

        private String layoutId; // 레이아웃 식별키
        private String name; // 레이아웃 이름
        private List<ProductModify> productList; // 상품 목록

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
    public static class ProductModify {

        private String productId; // 상품 식별키
        private String name; // 상풍명
        private int price; // 금액

        public Product toEntity(String layoutId) {
            return Product.builder()
                .name(this.name)
                .price(this.price)
                .layoutId(layoutId)
                .build();
        }

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

package dreamus.assignment.product.application.dto;

import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.Product;
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
}

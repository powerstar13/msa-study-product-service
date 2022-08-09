package dreamus.assignment.product.domain;

import dreamus.assignment.product.domain.shared.CommonDateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends CommonDateEntity { // 상품

    @Id
    @Column(value = "productId")
    private String productId; // 상품 식별키

    @Column(value = "name")
    private String name; // 레이아웃 이름

    @Column(value = "price")
    private int price; // 금액

    @Column(value = "layoutId")
    private String layoutId; // 레이아웃 식별키

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"productId\":\"" + productId + "\""
            + ", \"name\":\"" + name + "\""
            + ", \"price\":" + price
            + ", \"layoutId\":\"" + layoutId + "\""
            + "}";
    }
}

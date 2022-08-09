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
@Table(name = "layout")
public class Layout extends CommonDateEntity { // 레이아웃

    @Id
    @Column(value = "layoutId")
    private String layoutId; // 레이아웃 식별키

    @Column(value = "name")
    private String name; // 레이아웃 이름

    public void modify(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"layoutId\":\"" + layoutId + "\""
            + ", \"name\":\"" + name + "\""
            + "}";
    }
}

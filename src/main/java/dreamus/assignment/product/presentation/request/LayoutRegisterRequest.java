package dreamus.assignment.product.presentation.request;

import dreamus.assignment.product.infrastructure.exception.status.BadRequestException;
import dreamus.assignment.product.infrastructure.exception.status.ExceptionMessage;
import dreamus.assignment.product.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayoutRegisterRequest implements RequestVerify {

    private String name; // 레이아웃 이름

    @Override
    public void verify() {
        if (StringUtils.isBlank(name)) throw new BadRequestException(ExceptionMessage.IsRequiredLayoutName.getMessage());
    }

    @Override
    public String toString() {
        return "{"
            + "\"name\":\"" + name + "\""
            + "}";
    }
}

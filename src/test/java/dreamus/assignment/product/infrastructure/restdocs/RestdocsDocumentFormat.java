package dreamus.assignment.product.infrastructure.restdocs;

import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public class RestdocsDocumentFormat {
    
    public static Attributes.Attribute setFormat(String value) {
        return key("format").value(value);
    }

    public static Attributes.Attribute isRequiredForModifyFormat() {
        return setFormat("수정할 경우 필수");
    }
}

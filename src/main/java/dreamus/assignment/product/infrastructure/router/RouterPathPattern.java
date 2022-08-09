package dreamus.assignment.product.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    LAYOUT_ROOT("/layout", "/layout/**"),
    LAYOUT_REGISTER("/register", "/layout/register"),
    LAYOUT_INFO("/info/{layoutId}", "/layout/info/{layoutId}"),
    LAYOUT_LIST("/list", "/layout/list"),
    LAYOUT_MODIFY("/modify", "/layout/modify"),
    LAYOUT_DELETE("/delete/{layoutId}", "/layout/delete/{layoutId}"),

    PRODUCT_ROOT("/product", "/product/**"),
    PRODUCT_REGISTER("/register", "/product/register"),
    PRODUCT_INFO("/info/{productId}", "/product/info/{productId}"),
    PRODUCT_MODIFY("/modify", "/product/modify"),
    PRODUCT_DELETE("/delete/{productId}", "/product/delete/{layoutId}");

    private final String path;
    private final String fullPath;
}

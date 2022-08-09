package dreamus.assignment.product.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    LAYOUT_PRODUCT_ROOT("/layout-product", "/layout-product/**"),
    LAYOUT_PRODUCT_REGISTER("/register", "/layout-product/register"),
    LAYOUT_PRODUCT_MODIFY("/modify", "/layout-product/modify"),
    LAYOUT_PRODUCT_INFO("/info/{layoutId}", "/layout-product/info/{layoutId}"),
    LAYOUT_PRODUCT_LIST("/list", "/layout-product/list"),
    LAYOUT_PRODUCT_DELETE("/delete/{layoutId}", "/layout-product/delete/{layoutId}");

    private final String path;
    private final String fullPath;
}

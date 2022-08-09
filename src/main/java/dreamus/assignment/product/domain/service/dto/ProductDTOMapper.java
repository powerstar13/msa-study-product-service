package dreamus.assignment.product.domain.service.dto;

import dreamus.assignment.product.domain.Layout;
import dreamus.assignment.product.domain.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductDTOMapper {

    ProductDTO.LayoutProductInfo of(Layout layout, List<Product> productList);
}

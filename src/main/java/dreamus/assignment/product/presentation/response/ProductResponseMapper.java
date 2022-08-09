package dreamus.assignment.product.presentation.response;

import dreamus.assignment.product.domain.service.dto.ProductDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductResponseMapper {

    LayoutProductInfoResponse of(ProductDTO.LayoutProductInfo layoutProductInfo);

    LayoutProductListResponse of(ProductDTO.LayoutProductList layoutProductList);
}

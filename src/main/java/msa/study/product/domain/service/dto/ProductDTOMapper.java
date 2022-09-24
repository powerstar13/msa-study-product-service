package msa.study.product.domain.service.dto;

import msa.study.product.domain.Layout;
import msa.study.product.domain.Product;
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

package msa.study.product.presentation.request;

import msa.study.product.application.dto.ProductCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductRequestMapper {

    ProductCommand.LayoutProductRegister of(LayoutProductRegisterRequest request);

    ProductCommand.LayoutProductModify of(LayoutProductModifyRequest request);
}

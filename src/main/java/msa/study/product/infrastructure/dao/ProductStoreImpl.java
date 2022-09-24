package msa.study.product.infrastructure.dao;

import msa.study.product.application.dto.ProductCommand;
import msa.study.product.domain.Layout;
import msa.study.product.domain.Product;
import msa.study.product.domain.service.ProductStore;
import msa.study.product.domain.service.dto.ProductDTO;
import msa.study.product.infrastructure.exception.status.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductStoreImpl implements ProductStore {

    private final LayoutRepository layoutRepository;
    private final ProductRepository productRepository;

    /**
     * 레이아웃 상품 등록
     * @param command: 레이아웃 상품 정보
     * @return Layout: 레이아웃 레퍼런스
     */
    @Override
    public Mono<Layout> layoutProductRegister(ProductCommand.LayoutProductRegister command) {

        return layoutRepository.save(command.toEntity())
            .switchIfEmpty(Mono.error(ExceptionMessage.RegisterFailLayout.getException()))
            .flatMap(layout -> {
                List<Product> productList = command.getProductList().stream()
                    .map(productRegister -> productRegister.toEntity(layout.getLayoutId()))
                    .collect(Collectors.toList());

                return productRepository.saveAll(productList)
                    .switchIfEmpty(Mono.error(ExceptionMessage.RegisterFailProduct.getException()))
                    .then(Mono.just(layout));
            });
    }

    /**
     * 레이아웃 상품 수정
     * @param aggregate: 레이아웃 상품 애그리거트
     * @param command: 레이아웃 상품 정보
     */
    @Override
    public Mono<Void> layoutProductModify(ProductDTO.LayoutProductAggregate aggregate, ProductCommand.LayoutProductModify command) {

        Layout layout = aggregate.getLayout();
        layout.modify(command.getName()); // 수정할 레이아웃 정보 반영

        return layoutRepository.save(layout)
            .flatMap(updatedLayout -> {
                List<ProductCommand.ProductModify> productModifyList = command.getProductList();

                return Flux.fromIterable(aggregate.getProductList())
                    .flatMap(product -> {
                        if (productModifyList == null) return productRepository.delete(product); // 전달된 상품 정보가 없을 경우 기존 상품 정보는 삭제 처리

                        ProductCommand.ProductModify originProduct = productModifyList.stream()
                            .filter(productModify ->
                                product.getProductId().equals(productModify.getProductId())
                            )
                            .findFirst()
                            .orElse(null);

                        if (originProduct != null) {
                            product.modify(originProduct.getName(), originProduct.getPrice()); // 수정할 상품 정보 반영
                            productModifyList.remove(originProduct); // 처리된 상품 정보는 추가할 목록에서 제외 처리
                            return productRepository.save(product);
                        }

                        return productRepository.delete(product); // 목록에서 매칭되지 않은 기존 상품 정보는 삭제 처리
                    })
                    .flatMap(o -> {
                        // 추가할 상품 정보 취합
                        List<Product> productList = productModifyList.stream()
                            .map(productModify -> productModify.toEntity(updatedLayout.getLayoutId()))
                            .collect(Collectors.toList());

                        return productRepository.saveAll(productList); // 상품 정보 추가
                    })
                    .then();
            });
    }

    /**
     * 레이아웃 상품 삭제
     * @param aggregate: 레이아웃 상품 애그리거트
     */
    @Override
    public Mono<Void> layoutProductDelete(ProductDTO.LayoutProductAggregate aggregate) {

        return productRepository.deleteAll(aggregate.getProductList()) // 1. 상품 정보 삭제
            .then(layoutRepository.delete(aggregate.getLayout())); // 2. 레이아웃 정보 삭제
    }
}

package dev.kalyan.productservice.thirdpartyservice.productservice;

import dev.kalyan.productservice.dtos.FakeStoreProductDto;
import dev.kalyan.productservice.dtos.GenericProductDto;
import dev.kalyan.productservice.exceptions.NotFoundException;

import java.util.List;

public interface ThirdPartyProductService {
    FakeStoreProductDto getProductById(Long id) throws NotFoundException;

    FakeStoreProductDto createProduct(GenericProductDto product);

    FakeStoreProductDto deleteProductById(String id) throws NotFoundException;

    List<FakeStoreProductDto> getAllProducts();

    FakeStoreProductDto updateProductWithId(Long id, GenericProductDto productDto) throws NotFoundException;
}

package dev.kalyan.productservice.services;

import dev.kalyan.productservice.dtos.GenericProductDto;
import dev.kalyan.productservice.exceptions.NotFoundException;

import java.util.List;


public interface ProductService {
    GenericProductDto getProductById(Long id) throws NotFoundException;


    GenericProductDto createProduct(GenericProductDto product);

    GenericProductDto deleteProductById(String id) throws NotFoundException;

    List<GenericProductDto> getAllProducts();

    void updateProductWithId(Long id) throws NotFoundException;
}

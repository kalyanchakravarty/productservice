package dev.kalyan.productservice.services;

import dev.kalyan.productservice.dtos.GenericProductDto;
import dev.kalyan.productservice.exceptions.NotFoundException;
import dev.kalyan.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService{

    @Override
    public GenericProductDto getProductById(Long id) {
        return new GenericProductDto();
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto product) {
        return null;
    }

    @Override
    public GenericProductDto deleteProductById(String id) {
        return null;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        return null;
    }

    @Override
    public void updateProductWithId(Long id) throws NotFoundException {

    }
}

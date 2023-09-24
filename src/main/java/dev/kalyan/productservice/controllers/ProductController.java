package dev.kalyan.productservice.controllers;

import dev.kalyan.productservice.dtos.GenericProductDto;
import dev.kalyan.productservice.exceptions.NotFoundException;
import dev.kalyan.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService)
    {
        this.productService = productService;
    }
    @GetMapping("{id}")
    public GenericProductDto getProductById(@PathVariable("id") Long id) throws NotFoundException {
        return productService.getProductById(id);
    }

    //GET /products will give all the products
    @GetMapping
    public List<GenericProductDto> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @DeleteMapping("{id}")
    public GenericProductDto deleteProductById (@PathVariable("id") String id) throws NotFoundException {
        return productService.deleteProductById(id);
    }

    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto product)
    {
        return productService.createProduct(product);
    }

    @PutMapping("{id}")
    public GenericProductDto updateProductWithId(@PathVariable("id") Long id, @RequestBody GenericProductDto product) throws NotFoundException
    {
        return productService.updateProductWithId(id, product);
    }


}

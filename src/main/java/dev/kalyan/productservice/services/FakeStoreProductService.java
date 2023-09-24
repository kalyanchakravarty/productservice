package dev.kalyan.productservice.services;

import dev.kalyan.productservice.dtos.FakeStoreProductDto;
import dev.kalyan.productservice.dtos.GenericProductDto;
import dev.kalyan.productservice.exceptions.NotFoundException;
import dev.kalyan.productservice.thirdpartyservice.productservice.FakeStoreProductServiceClient;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private final FakeStoreProductServiceClient fakeStoreProductServiceClient;

    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient )
    {
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }
    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException{
        return convertFakeStoreDtoToGenericProductDto(fakeStoreProductServiceClient.getProductById(id));
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto product) {
        return convertFakeStoreDtoToGenericProductDto(fakeStoreProductServiceClient.createProduct(product));
    }

    @Override
    public GenericProductDto deleteProductById(String id) throws NotFoundException{
        return convertFakeStoreDtoToGenericProductDto(fakeStoreProductServiceClient.deleteProductById(id));
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<GenericProductDto> answer = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductServiceClient.getAllProducts())
        {
            GenericProductDto product = convertFakeStoreDtoToGenericProductDto(fakeStoreProductDto);
            answer.add(product);
        }
        return answer;
    }

    @Override
    public GenericProductDto updateProductWithId(Long id, GenericProductDto productDto) throws NotFoundException {
        return convertFakeStoreDtoToGenericProductDto(fakeStoreProductServiceClient.updateProductWithId(id, productDto));
    }

    private GenericProductDto convertFakeStoreDtoToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto product = new GenericProductDto();
        product.setId(fakeStoreProductDto.getId());
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setCategory(fakeStoreProductDto.getCategory());

        return product;
    }

}

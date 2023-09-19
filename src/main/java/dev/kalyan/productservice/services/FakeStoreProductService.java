package dev.kalyan.productservice.services;

import dev.kalyan.productservice.dtos.FakeStoreProductDto;
import dev.kalyan.productservice.dtos.GenericProductDto;
import dev.kalyan.productservice.exceptions.NotFoundException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplateBuilder restTemplateBuilder;
    private String getProductRequestURL = "https://fakestoreapi.com/products/{id}";
    private String createProductRequestURL = "https://fakestoreapi.com/products/";



//    private String specificProductRequestUrl ;
//    private String productRequestsBaseUrl ;
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplateBuilder = restTemplateBuilder;
        //this.productRequestsBaseUrl  = fakeStoreApiUrl + fakeStoreProductsApiPath;
        //this.specificProductRequestUrl = fakeStoreApiUrl + fakeStoreProductsApiPath + "/{id}";
    }
    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException{
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response =  restTemplate.getForEntity(getProductRequestURL, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if (fakeStoreProductDto == null)
        {
            String message = "product with id: " +id+ " does not exist";
            throw new NotFoundException(message);
        }
        return convertFakeStoreDtoToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDto> response = restTemplate.postForEntity(createProductRequestURL, product, GenericProductDto.class);
        return response.getBody();
    }

    @Override
    public GenericProductDto deleteProductById(String id) throws NotFoundException{
        String specificProductRequestUrl =  createProductRequestURL + "/{id}";
        RestTemplate restTemplate = restTemplateBuilder.build();

        //RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        //ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        //ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(deleteResource, HttpMethod.DELETE, requestCallback, responseExtractor, id);

        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(specificProductRequestUrl, HttpMethod.DELETE,
                requestCallback, responseExtractor, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if (fakeStoreProductDto == null)
        {
            String message = "product with id: " +id+ " does not exist";
            throw new NotFoundException(message);
        }
        return convertFakeStoreDtoToGenericProductDto(fakeStoreProductDto);
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

    @Override
    public List<GenericProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto[]> response =  restTemplate.getForEntity(createProductRequestURL, FakeStoreProductDto[].class);
        List<GenericProductDto> answer = new ArrayList<GenericProductDto>();
        for(FakeStoreProductDto fakeStoreProductDto : Arrays.stream(response.getBody()).toList())
        {
            GenericProductDto product = convertFakeStoreDtoToGenericProductDto(fakeStoreProductDto);
            answer.add(product);
        }
        return answer;
    }

    @Override
    public void updateProductWithId(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //ResponseEntity<FakeStoreProductDto>
    }


}

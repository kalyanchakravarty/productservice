package dev.kalyan.productservice.thirdpartyservice.productservice;

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
import java.util.List;

/*
Wrapper over FakeStoreProduct API
*/

@Service
public class FakeStoreProductServiceClient implements ThirdPartyProductService{

    private final RestTemplate restTemplate;
    private final String createProductRequestURL = "https://fakestoreapi.com/products/";
    private final String specificProductRequestUrl =  createProductRequestURL + "/{id}";

    public FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplate = restTemplateBuilder.build();
        //this.productRequestsBaseUrl  = fakeStoreApiUrl + fakeStoreProductsApiPath;
        //this.specificProductRequestUrl = fakeStoreApiUrl + fakeStoreProductsApiPath + "/{id}";
    }
    @Override
    public FakeStoreProductDto getProductById(Long id) throws NotFoundException{
        String getProductRequestURL = "https://fakestoreapi.com/products/{id}";
        ResponseEntity<FakeStoreProductDto> response =  this.restTemplate.getForEntity(getProductRequestURL, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if (fakeStoreProductDto == null)
        {
            String message = "product with id: " +id+ " does not exist";
            throw new NotFoundException(message);
        }
        return fakeStoreProductDto;
    }

    @Override
    public FakeStoreProductDto createProduct(GenericProductDto product) {
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.postForEntity(createProductRequestURL, product, FakeStoreProductDto.class);
        return response.getBody();
    }

    @Override
    public FakeStoreProductDto deleteProductById(String id) throws NotFoundException{
        RequestCallback requestCallback = this.restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                this.restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.execute(specificProductRequestUrl, HttpMethod.DELETE,
                requestCallback, responseExtractor, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if (fakeStoreProductDto == null)
        {
            String message = "product with id: " +id+ " does not exist";
            throw new NotFoundException(message);
        }
        return fakeStoreProductDto;
    }


    @Override
    public List<FakeStoreProductDto> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response =  this.restTemplate.getForEntity(createProductRequestURL, FakeStoreProductDto[].class);
        return List.of(response.getBody());
    }

    @Override
    public FakeStoreProductDto updateProductWithId(Long id, GenericProductDto productDto) throws NotFoundException {
        RequestCallback requestCallback = this.restTemplate.httpEntityCallback(productDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = this.restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = this.restTemplate.execute(specificProductRequestUrl, HttpMethod.PUT, requestCallback, responseExtractor, id);
        if (fakeStoreProductDtoResponseEntity == null) {
            String message = "product with id: " +id+ " does not exist";
            throw new NotFoundException(message);
        }
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if(fakeStoreProductDto == null)
        {
            String message = "product with id: " +id+ " does not exist";
            throw new NotFoundException(message);
        }
        return fakeStoreProductDto;
    }
}

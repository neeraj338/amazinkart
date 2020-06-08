package com.amazinkart.domain.product;

import com.amazinkart.adapters.exchange.ExchangeApiPort;
import com.amazinkart.adapters.product.ProductApiPort;
import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock private ProductApiPort productPort;

  @Mock private ExchangeApiPort exchangePort;

  @Mock private List<IDiscount> discounts;

  @Mock private ProductJsonFileWriter productJsonFileWriter;

  @InjectMocks private ProductService productService;

  @Test
  public void testGetProductDetails() throws IOException {
    List<ProductResponse> responsesList = new ArrayList<>();
    ProductResponse product =
        ProductResponse.builder()
            .category("books")
            .inventory(5)
            .rating(4.8)
            .currency("USD")
            .price(49.55)
            .origin("America")
            .product("Zen and The art of motorcycle maintenance")
            .build();
    responsesList.add(product);
    Mockito.when(productPort.getProducts()).thenReturn(Optional.of(responsesList));

    Mockito.when(exchangePort.toINR(product.getCurrency(), product.getPrice()))
        .thenReturn(50 * 90d);
    this.productService.getProductDetails();

    Mockito.verify(productJsonFileWriter).writeToFile(responsesList);
  }
}

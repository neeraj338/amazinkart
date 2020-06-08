package com.amazinkart.domain.product;

import com.amazinkart.adapters.exchange.ExchangeApiPort;
import com.amazinkart.adapters.product.ProductApiPort;
import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

  private final ProductApiPort productPort;

  private final ExchangeApiPort exchangePort;

  private final List<IDiscount> discounts;

  private final ProductJsonFileWriter productJsonFileWriter;

  public void getProductDetails() throws IOException {

    Optional<List<ProductResponse>> products = productPort.getProducts();
    if (products.isPresent()) {
      for (ProductResponse product : products.get()) {
        log.info(" product :: {} ", product);
        double exchangePrice = exchangePort.toINR(product.getCurrency(), product.getPrice());
        product.setPrice(exchangePrice);
        product.setCurrency("INR");
        discounts.stream()
            .map(x -> x.calculateDiscount(product))
            .filter(x -> x.isPresent())
            .map(x -> x.get())
            .max(Comparator.comparing(ProductDiscount::getAmount))
            .ifPresent(
                x -> {
                  product.setDiscount(x);
                });
      }
      productJsonFileWriter.writeToFile(products.get());
    } else {
      log.info(" No product found !");
    }
  }
}

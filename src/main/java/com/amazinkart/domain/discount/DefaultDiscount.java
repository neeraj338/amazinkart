package com.amazinkart.domain.discount;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.product.ProductDiscount;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultDiscount implements IDiscount {

  private boolean test(ProductResponse product) {
    return product.getPrice() > 1000;
  }

  @Override
  public Optional<ProductDiscount> calculateDiscount(ProductResponse product) {
    if (test(product)) {
      return Optional.of(
          ProductDiscount.builder()
              .discountTag("get 2% off")
              .amount((product.getPrice() * 0.02))
              .build());
    }
    return Optional.empty();
  }
}

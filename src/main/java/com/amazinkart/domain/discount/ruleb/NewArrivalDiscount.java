package com.amazinkart.domain.discount.ruleb;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "promotionsetb", havingValue = "true")
public class NewArrivalDiscount implements IDiscount {
  @Override
  public Optional<ProductDiscount> calculateDiscount(ProductResponse product) {
    if ("new".equalsIgnoreCase(product.getArrival())) {
      return Optional.of(
          ProductDiscount.builder()
              .discountTag("get 7% off")
              .amount((product.getPrice() * 0.07))
              .build());
    }
    return Optional.empty();
  }
}

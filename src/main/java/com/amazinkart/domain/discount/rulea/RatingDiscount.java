package com.amazinkart.domain.discount.rulea;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "promotionseta", havingValue = "true")
public class RatingDiscount implements IDiscount {

  @Override
  public Optional<ProductDiscount> calculateDiscount(ProductResponse product) {

    if (product.getRating() == 2) {

      return Optional.of(
          ProductDiscount.builder()
              .discountTag("get 2% off")
              .amount((product.getPrice() * 0.02))
              .build());
    } else if (product.getRating() < 2) {

      return Optional.of(
          ProductDiscount.builder()
              .discountTag("get 8% off")
              .amount((product.getPrice() * 0.08))
              .build());
    }
    return Optional.empty();
  }
}

package com.amazinkart.domain.discount.ruleb;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "promotionsetb", havingValue = "true")
public class LargeInventoryDiscount implements IDiscount {
  @Override
  public Optional<ProductDiscount> calculateDiscount(ProductResponse product) {
    if (product.getInventory() > 20) {
      return Optional.of(
          ProductDiscount.builder()
              .discountTag("get 12% off")
              .amount((product.getPrice() * 0.12))
              .build());
    }
    return Optional.empty();
  }
}

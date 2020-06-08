package com.amazinkart.domain.discount.rulea;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "promotionseta", havingValue = "true")
public class DiscountOnCategory implements IDiscount {
  private static final List<String> productCategories = Arrays.asList("electronics", "furnishing");

  @Override
  public Optional<ProductDiscount> calculateDiscount(ProductResponse product) {

    if (product.getPrice() > 500 && productCategories.contains(product.getCategory())) {

      return Optional.of(ProductDiscount.builder().discountTag("get 100 off").amount(100).build());
    }
    return Optional.empty();
  }
}

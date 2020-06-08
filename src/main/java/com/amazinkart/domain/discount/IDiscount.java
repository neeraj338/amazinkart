package com.amazinkart.domain.discount;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.product.ProductDiscount;

import java.util.Optional;

public interface IDiscount {
  Optional<ProductDiscount> calculateDiscount(ProductResponse product);
}

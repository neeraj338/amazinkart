package com.amazinkart.adapters.product;

import com.amazinkart.domain.product.ProductDiscount;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductResponse {

  private String category;
  private long inventory;
  private double rating;
  @Setter private String currency;

  @Setter private double price;

  private String arrival;
  private String origin;
  private String product;

  @Setter private ProductDiscount discount;
}

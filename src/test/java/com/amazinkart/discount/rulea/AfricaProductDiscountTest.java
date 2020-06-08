package com.amazinkart.discount.rulea;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.discount.rulea.AfricaProductDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class AfricaProductDiscountTest {

  IDiscount discount = new AfricaProductDiscount();

  @Test
  @DisplayName(" should give discount of 7% ")
  public void testCalculateDiscount() {
    ProductResponse product =
        ProductResponse.builder()
            .category("apparels")
            .inventory(10)
            .rating(4.2)
            .currency("ZAR")
            .price(429.5)
            .origin("Africa")
            .product("hawaiian shirts")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(true));

    Assert.assertThat(
        discountResult.get().getAmount(), Matchers.closeTo((product.getPrice() * .07), 0.1));
  }

  @Test
  @DisplayName(" should not give any discount ")
  public void testCalculateNoDiscount() {
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

    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(false));
  }
}

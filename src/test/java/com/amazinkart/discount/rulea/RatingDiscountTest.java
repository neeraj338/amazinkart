package com.amazinkart.discount.rulea;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.discount.rulea.RatingDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class RatingDiscountTest {

  private IDiscount discount = new RatingDiscount();

  @Test
  @DisplayName(" should give 2% ")
  public void testCalculateDiscount() {
    ProductResponse product =
        ProductResponse.builder()
            .category("electronics")
            .inventory(10)
            .rating(2)
            .currency("INR")
            .price(2500)
            .origin("Asia")
            .product("XTP mobile")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(true));

    Assert.assertThat(
        discountResult.get().getAmount(), Matchers.closeTo(product.getPrice() * 0.02, 0.0));
  }

  @Test
  @DisplayName(" should give 8% ")
  public void testCalculateDiscountEightPercentage() {
    ProductResponse product =
        ProductResponse.builder()
            .category("electronics")
            .inventory(10)
            .rating(1.2)
            .currency("INR")
            .price(2500)
            .origin("Asia")
            .product("XTP mobile")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(true));

    Assert.assertThat(
        discountResult.get().getAmount(), Matchers.closeTo(product.getPrice() * 0.08, 0.1));
  }

  @Test
  @DisplayName(" should give no discount rating is > 2 ")
  public void testCalculateDiscountNoDiscount() {
    ProductResponse product =
        ProductResponse.builder()
            .category("electronics")
            .inventory(10)
            .rating(3.5)
            .currency("INR")
            .price(2500)
            .origin("Asia")
            .product("XTP mobile")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(false));
  }
}

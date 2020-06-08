package com.amazinkart.discount.ruleb;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.discount.ruleb.LargeInventoryDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class LargeInventoryDiscountTest {

  IDiscount discount = new LargeInventoryDiscount();

  @Test
  @DisplayName(" should give discount of 12% if inventory > 20 pc.")
  public void testCalculateDiscount() {
    ProductResponse product =
        ProductResponse.builder()
            .category("apparels")
            .inventory(21)
            .rating(4.2)
            .currency("ZAR")
            .price(1500)
            .origin("Africa")
            .product("hawaiian shirts")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(true));

    Assert.assertThat(
        discountResult.get().getAmount(), Matchers.closeTo((product.getPrice() * .12), 0.0));
  }

  @Test
  @DisplayName(" should give No discount if inventory == 20 pc.")
  public void testCalculateDiscountNoDiscountOn20() {
    ProductResponse product =
        ProductResponse.builder()
            .category("apparels")
            .inventory(20)
            .rating(4.2)
            .currency("ZAR")
            .price(1500)
            .origin("Africa")
            .product("hawaiian shirts")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(false));
  }
}

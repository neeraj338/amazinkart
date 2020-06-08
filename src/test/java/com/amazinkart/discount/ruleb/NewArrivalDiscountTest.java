package com.amazinkart.discount.ruleb;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.discount.ruleb.NewArrivalDiscount;
import com.amazinkart.domain.product.ProductDiscount;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class NewArrivalDiscountTest {

  IDiscount discount = new NewArrivalDiscount();

  @Test
  @DisplayName(" should give discount on new arrivals 7% off ")
  public void testCalculateDiscount() {
    ProductResponse product =
        ProductResponse.builder()
            .category("accessories")
            .inventory(10)
            .rating(0)
            .arrival("NEW")
            .currency("INR")
            .price(2199)
            .origin("America")
            .product("BOA power bank")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(true));

    Assert.assertThat(
        discountResult.get().getAmount(), Matchers.closeTo((product.getPrice() * 0.07), 0.1));
  }

  @Test
  @DisplayName(" should not give discount on regular products  ")
  public void testCalculateDiscountNoDiscountOnRegularProducts() {
    ProductResponse product =
        ProductResponse.builder()
            .category("accessories")
            .inventory(10)
            .rating(0)
            // .arrival("NEW")
            .currency("INR")
            .price(2199)
            .origin("America")
            .product("BOA power bank")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(false));
  }
}

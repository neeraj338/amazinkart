package com.amazinkart.discount.rulea;

import com.amazinkart.adapters.product.ProductResponse;
import com.amazinkart.domain.discount.IDiscount;
import com.amazinkart.domain.discount.rulea.DiscountOnCategory;
import com.amazinkart.domain.product.ProductDiscount;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class DiscountOnCategoryTest {

  private IDiscount discount = new DiscountOnCategory();

  @Test
  @DisplayName(" should give discount of Rs 100 ")
  public void testCalculateDiscount() {
    ProductResponse product =
        ProductResponse.builder()
            .category("electronics")
            .inventory(10)
            .rating(3.8)
            .currency("INR")
            .price(2500)
            .origin("Asia")
            .product("XTP mobile")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(true));

    Assert.assertThat(discountResult.get().getAmount(), Matchers.closeTo(100, 0.0));
  }

  @Test
  @DisplayName(" should Not give discount if price < 500 and category is electronics")
  public void testCalculateNoDiscountPriceLessThanEq500() {
    ProductResponse product =
        ProductResponse.builder()
            .category("electronics")
            .inventory(10)
            .rating(3.8)
            .currency("INR")
            .price(500)
            .origin("Asia")
            .product("XTP mobile")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(false));
  }

  @Test
  @DisplayName(" should Not give discount if category is not in ( electronics, furnishing)")
  public void testCalculateNoDiscountCategoryNotIn() {
    ProductResponse product =
        ProductResponse.builder()
            .category("clothing")
            .inventory(10)
            .rating(3.8)
            .currency("INR")
            .price(1500)
            .origin("Asia")
            .product("shirts")
            .build();
    Optional<ProductDiscount> discountResult = discount.calculateDiscount(product);

    Assert.assertThat(discountResult.isPresent(), Matchers.equalTo(false));
  }
}

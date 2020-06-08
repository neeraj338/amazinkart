package com.amazinkart.adapters.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitProductApiConfig {

  private final String baseUrl;

  public RetrofitProductApiConfig(@Value("${api.product-details.base-url}") String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Bean
  public ProductApiClient getProductApiClient() {

    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    return retrofit.create(ProductApiClient.class);
  }
}

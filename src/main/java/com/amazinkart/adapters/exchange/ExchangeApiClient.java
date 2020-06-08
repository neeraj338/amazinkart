package com.amazinkart.adapters.exchange;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ExchangeApiClient {

  @Headers("Content-Type: application/json")
  @GET("/latest")
  Call<ExchangeResponse> getRates();
}

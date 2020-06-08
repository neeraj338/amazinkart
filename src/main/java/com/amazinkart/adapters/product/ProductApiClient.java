package com.amazinkart.adapters.product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import java.io.IOException;
import java.util.List;

public interface ProductApiClient {

  @Headers("Content-Type: application/json")
  @GET("/b/5d31a1c4536bb970455172ca/latest")
  Call<List<ProductResponse>> getProducts() throws IOException;
}

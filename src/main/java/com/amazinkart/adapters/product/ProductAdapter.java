package com.amazinkart.adapters.product;

import com.amazinkart.exception.DownstreamException;
import com.amazinkart.exception.DownstreamTimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class ProductAdapter implements ProductApiPort {

  private final ProductApiClient productApiClient;

  @Override
  public Optional<List<ProductResponse>> getProducts() {
    try {
      Response<List<ProductResponse>> execute = productApiClient.getProducts().execute();
      List<ProductResponse> resultList = execute.body();
      return Optional.of(resultList);
    } catch (SocketTimeoutException e) {
      log.error("Call to api timed out for {}", e);
      throw new DownstreamTimeoutException("timeout :: https://api.jsonbin.io");
    } catch (IOException e) {
      log.error("IOException exception occurred {}", e);
      throw new DownstreamException(e.getMessage());
    }
  }
}

package com.amazinkart.domain.product;

import com.amazinkart.adapters.product.ProductResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ProductJsonFileWriter {

  private final Gson gson;

  private final String jsonFilePath;

  public ProductJsonFileWriter(
      Gson gson, @Value("${json.filepath:build/output.json}") String jsonFilePath) {
    this.gson = gson;
    this.jsonFilePath = jsonFilePath;
  }

  public void writeToFile(List<ProductResponse> products) throws IOException {
    log.info("saving to file \t \n {}", gson.toJson(products));

    try (FileWriter writer = new FileWriter(jsonFilePath)) {
      gson.toJson(products, writer);
    }
  }
}

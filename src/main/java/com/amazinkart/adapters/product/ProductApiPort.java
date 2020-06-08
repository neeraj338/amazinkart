package com.amazinkart.adapters.product;

import java.util.List;
import java.util.Optional;

public interface ProductApiPort {

  Optional<List<ProductResponse>> getProducts();
}

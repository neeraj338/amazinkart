package com.amazinkart.adapters.product;

import com.amazinkart.exception.DownstreamException;
import com.amazinkart.exception.DownstreamTimeoutException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductAdapterTest {

  private static MockWebServer server;

  private static ProductAdapter productAdapter;

  @BeforeEach
  public void setUp() throws IOException {
    server = new MockWebServer();
    server.start();

    RetrofitProductApiConfig config =
        new RetrofitProductApiConfig(
            String.format("http://%s:%d", server.getHostName(), server.getPort()));

    productAdapter = new ProductAdapter(config.getProductApiClient());
  }

  @Test
  @DisplayName("should get the exchange rates.")
  public void testGetRates() {

    server.enqueue(new MockResponse().setResponseCode(200).setBody(getMockedProductResponse()));
    Optional<List<ProductResponse>> products = productAdapter.getProducts();
    Assert.assertThat(products.isPresent(), Matchers.is(Matchers.equalTo(true)));
  }

  @Test
  @DisplayName("Should throw timeout exception")
  public void testTimeOutException() {
    server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));
    Assertions.assertThrows(DownstreamTimeoutException.class, () -> productAdapter.getProducts());
  }

  @Test
  @DisplayName("Should throw DownstreamException")
  public void testDownStreamException() {
    server.enqueue(new MockResponse().setResponseCode(200).setBody("lol"));
    Assertions.assertThrows(DownstreamException.class, () -> productAdapter.getProducts());
  }

  @AfterEach
  private void tearDown() throws IOException {
    server.shutdown();
  }

  private static String getMockedProductResponse() {
    return "[\n"
        + "  {\n"
        + "    \"category\": \"electronics\",\n"
        + "    \"inventory\": 10,\n"
        + "    \"rating\": 3.8,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 2500,\n"
        + "    \"origin\": \"Asia\",\n"
        + "    \"product\": \"XTP mobile\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"electronics\",\n"
        + "    \"inventory\": 5,\n"
        + "    \"rating\": 1.8,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 4500,\n"
        + "    \"origin\": \"Asia\",\n"
        + "    \"product\": \"Maximize mobile\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"clothing\",\n"
        + "    \"inventory\": 50,\n"
        + "    \"rating\": 4.8,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 1800,\n"
        + "    \"origin\": \"Asia\",\n"
        + "    \"product\": \"Jeet white shoes\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"grooming\",\n"
        + "    \"inventory\": 10,\n"
        + "    \"rating\": 2,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 150,\n"
        + "    \"origin\": \"Europe\",\n"
        + "    \"product\": \"Pearly tooth brush\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"accessories\",\n"
        + "    \"inventory\": 10,\n"
        + "    \"arrival\": \"NEW\",\n"
        + "    \"rating\": 0,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 2199,\n"
        + "    \"origin\": \"America\",\n"
        + "    \"product\": \"BOA power bank\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"toys\",\n"
        + "    \"inventory\": 10,\n"
        + "    \"rating\": 2.8,\n"
        + "    \"currency\": \"JPY\",\n"
        + "    \"price\": 6099,\n"
        + "    \"origin\": \"Asia\",\n"
        + "    \"product\": \"remote control car\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"books\",\n"
        + "    \"inventory\": 5,\n"
        + "    \"rating\": 4.8,\n"
        + "    \"currency\": \"USD\",\n"
        + "    \"price\": 49.55,\n"
        + "    \"origin\": \"America\",\n"
        + "    \"product\": \"Zen and The art of motorcycle maintenance\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"apparels\",\n"
        + "    \"inventory\": 10,\n"
        + "    \"rating\": 4.2,\n"
        + "    \"currency\": \"ZAR\",\n"
        + "    \"price\": 429.5,\n"
        + "    \"origin\": \"Africa\",\n"
        + "    \"product\": \"hawaiian shirts\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"home\",\n"
        + "    \"inventory\": 30,\n"
        + "    \"rating\": 4.2,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 490,\n"
        + "    \"origin\": \"Africa\",\n"
        + "    \"product\": \"water bottle\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"accessories\",\n"
        + "    \"inventory\": 10,\n"
        + "    \"rating\": 3.2,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 190,\n"
        + "    \"origin\": \"Asia\",\n"
        + "    \"product\": \"iron man mousepad\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"category\": \"furnishing\",\n"
        + "    \"inventory\": 45,\n"
        + "    \"rating\": 3.2,\n"
        + "    \"currency\": \"INR\",\n"
        + "    \"price\": 190,\n"
        + "    \"origin\": \"Asia\",\n"
        + "    \"product\": \"lunch box\"\n"
        + "  }\n"
        + "]";
  }
}

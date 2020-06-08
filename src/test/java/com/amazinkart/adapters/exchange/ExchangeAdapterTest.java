package com.amazinkart.adapters.exchange;

import com.amazinkart.exception.DownstreamException;
import com.amazinkart.exception.DownstreamTimeoutException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Optional;

public class ExchangeAdapterTest {

  private static MockWebServer server;

  private static ExchangeAdapter exchangeAdapter;

  @BeforeEach
  public void setUp() throws IOException {
    server = new MockWebServer();
    server.start();

    RetrofitExchangeApiConfig config =
        new RetrofitExchangeApiConfig(
            String.format("http://%s:%d", server.getHostName(), server.getPort()));

    exchangeAdapter = new ExchangeAdapter(config.getExchangeClient(), Optional.empty());
  }

  @AfterEach
  private void tearDown() throws IOException {
    server.shutdown();
  }

  private static String exchangeMockResponse() {
    return "{\n"
        + "  \"rates\": {\n"
        + "    \"CAD\": 1.5237,\n"
        + "    \"HKD\": 8.7809,\n"
        + "    \"ISK\": 148.9,\n"
        + "    \"PHP\": 56.457,\n"
        + "    \"DKK\": 7.4564,\n"
        + "    \"HUF\": 344.62,\n"
        + "    \"CZK\": 26.589,\n"
        + "    \"AUD\": 1.6227,\n"
        + "    \"RON\": 4.8382,\n"
        + "    \"SEK\": 10.425,\n"
        + "    \"IDR\": 15882.4,\n"
        + "    \"INR\": 85.63,\n"
        + "    \"BRL\": 5.7329,\n"
        + "    \"RUB\": 77.8155,\n"
        + "    \"HRK\": 7.5715,\n"
        + "    \"JPY\": 123.77,\n"
        + "    \"THB\": 35.65,\n"
        + "    \"CHF\": 1.0866,\n"
        + "    \"SGD\": 1.5775,\n"
        + "    \"PLN\": 4.4425,\n"
        + "    \"BGN\": 1.9558,\n"
        + "    \"TRY\": 7.6747,\n"
        + "    \"CNY\": 8.0349,\n"
        + "    \"NOK\": 10.5403,\n"
        + "    \"NZD\": 1.7392,\n"
        + "    \"ZAR\": 19.0823,\n"
        + "    \"USD\": 1.133,\n"
        + "    \"MXN\": 24.6466,\n"
        + "    \"ILS\": 3.9172,\n"
        + "    \"GBP\": 0.89448,\n"
        + "    \"KRW\": 1365.57,\n"
        + "    \"MYR\": 4.8345\n"
        + "  },\n"
        + "  \"base\": \"EUR\",\n"
        + "  \"date\": \"2020-06-05\"\n"
        + "}";
  }

  @Test
  @DisplayName("should get the exchange rates.")
  public void testGetRates() {
    server.enqueue(new MockResponse().setResponseCode(200).setBody(exchangeMockResponse()));
    Optional<ExchangeResponse> rates = exchangeAdapter.getRates();
    Assert.assertThat(rates.isPresent(), Matchers.is(Matchers.equalTo(true)));
  }

  @Test
  @DisplayName(" should return the same amount is source and target currencies are same.")
  public void testExchangeFromINRtoINR() {
    server.enqueue(new MockResponse().setResponseCode(200).setBody(exchangeMockResponse()));
    double inrAmount = exchangeAdapter.toINR("INR", 2.90);
    Assert.assertThat(inrAmount, Matchers.is(Matchers.equalTo(2.90)));
  }

  @Test
  @DisplayName(" should perform the conversion using EUR as base")
  public void testExchangeFromGBPtoINR() {
    server.enqueue(new MockResponse().setResponseCode(200).setBody(exchangeMockResponse()));
    double inrAmount = exchangeAdapter.toINR("GBP", 1);
    Assert.assertThat(inrAmount, Matchers.is(Matchers.closeTo(95.73, 0.1)));
  }

  @Test
  @DisplayName("Should throw timeout exception")
  public void testTimeOutException() {
    server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));
    Assertions.assertThrows(DownstreamTimeoutException.class, () -> exchangeAdapter.getRates());
  }

  @Test
  @DisplayName("Should throw DownstreamException")
  public void testDownStreamException() {
    server.enqueue(new MockResponse().setResponseCode(200).setBody("lol"));
    Assertions.assertThrows(DownstreamException.class, () -> exchangeAdapter.getRates());
  }
}

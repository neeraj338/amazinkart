package com.amazinkart;

import com.amazinkart.domain.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class Application {

  private static final java.util.List<String> commandArg =
      Arrays.asList("promotionSetB", "promotionSetA");

  public static void main(String[] args) throws IOException {
    String arg = null;
    if (args.length == 0) {
      log.warn(
          "please supply valid argument promotionSetA or promotionSetB, using default promotionSetA");
    } else {
      arg = args[0];
      if (!commandArg.contains(arg)) {
        arg = null;
        log.warn("Bad argument supplied using default : promotionSetA");
      }
    }
    if (arg == null) {
      arg = "promotionSetA";
    }
    log.info(" using Promotion Set:: =>  {} ", arg);
    System.setProperty(arg.toLowerCase(), "true");
    ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    ProductService productService = applicationContext.getBean(ProductService.class);
    productService.getProductDetails();
    log.info(" completed !! please refer build/output.json");
    System.exit(0);
  }
}

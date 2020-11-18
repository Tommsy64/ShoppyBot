package model.store;

import java.io.IOException;
import model.product.Product;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents a store this bot will know how to handle.
 */
public interface IStore {

  /**
   * Purchases the given product if the total bought so far is less than the max number to buy.
   *
   * @param product the product to purchase
   * @param driver  the chrome driver
   * @throws IllegalStateException    thrown when something unexpected happened.
   * @throws IllegalArgumentException thrown when the given product is not offered by the store
   */
  void purchaseProduct(Product product, ChromeDriver driver)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Logs into the online store account (for purchases later).
   *
   * @param driver the chrome driver
   */
  void login(ChromeDriver driver) throws IOException;

}

package model.store;

import model.product.Product;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents a store this bot will know how to handle.
 */
public interface IStore {

  /**
   * Returns {@code true} if the product is in stock.
   *
   * @param product the product to check
   * @param driver  the browser driver to check if the product is in stock
   * @return {@code true} if the product is in stock
   * @throws IllegalStateException    thrown when the checkout button says something unexpected
   * @throws IllegalArgumentException thrown when the given product is not offered by the store
   */
  boolean isInStock(Product product, ChromeDriver driver)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Purchases the given product.
   *
   * @param product the product to purchase
   * @throws IllegalStateException    thrown when the product is out of stock or something
   *                                  unexpected happened.
   * @throws IllegalArgumentException thrown when the given product is not offered by the store
   */
  void purchaseProduct(Product product, ChromeDriver driver)
      throws IllegalStateException, IllegalArgumentException;

}

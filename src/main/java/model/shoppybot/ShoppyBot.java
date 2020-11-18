package model.shoppybot;

import java.io.IOException;
import model.product.Product;
import model.store.IStore;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Represents a bot that tries to purchase a given product from a given store.
 */
public class ShoppyBot implements Runnable {

  private Product product;
  private IStore store;
  private ChromeDriver driver;
  private boolean headless;

  /**
   * Constructs a {@code ShopBot} to purchase an item from a store.
   *
   * @param product  the product to purchase
   * @param store    the store to purchase from
   * @param headless if the browser should be in headless mode or not
   */

  public ShoppyBot(Product product, IStore store, boolean headless) {
    this.product = product;
    this.store = store;
    this.headless = headless;
  }

  /**
   * Constructs a {@code ShopBot} without headless mode.
   *
   * @param product the product to purchase
   * @param store   the store to purchase from
   */
  public ShoppyBot(Product product, IStore store) {
    this(product, store, false);
  }

  @Override
  public void run() {
    this.initDriver();
    try {
      this.store.login(this.driver);
      this.store.purchaseProduct(product, this.driver);
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  /**
   * Initializes the chrome driver.
   */
  private void initDriver() {
    if (this.headless) {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless", "--disable-gpu", "--window-size=500,500",
          "--ignore-certificate-errors");
      this.driver = new ChromeDriver(options);
    } else {
      this.driver = new ChromeDriver();
    }
  }
}

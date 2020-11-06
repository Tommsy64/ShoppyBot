package model.bot;

import java.util.concurrent.atomic.AtomicInteger;
import model.product.Product;
import model.store.IStore;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Represents a bot that tries to purchase a given product.
 */
public class ShopBot implements Runnable {

  private Product product;
  private IStore store;
  private ChromeDriver driver;
  private AtomicInteger boughtSoFar;
  private int id;
  private int maxToBuy;
  private boolean headless;

  /**
   * Default constructor that takes in a product and a store. This bot will try to purchase the
   * given product from the given store.
   *
   * @param product the product to purchase
   * @param store   the store to purchase the product from
   */
  public ShopBot(Product product, IStore store) {
    this.product = product;
    this.store = store;
    this.boughtSoFar = new AtomicInteger(0);
    this.id = product.hashCode() + store.hashCode();
    this.maxToBuy = 1;
    this.headless = true;
  }

  /**
   * Convenience constructor that initializes a {@code ShopBot} with a specific id and in headless
   * mode or not.
   *
   * @param product  the product to purchase
   * @param store    the store to purchase the product from
   * @param id       the id of this bot
   * @param headless if it shoudl be started in headless mode
   */
  public ShopBot(Product product, IStore store, int id, boolean headless) {
    this(product, store);
    this.id = id;
    this.headless = headless;
  }

  /**
   * Convenience constructor that initializes a {@code ShopBot} with a specific id and a reference
   * to the amount bought so far as well as the max amount of products to buy.
   *
   * @param product     the product to purchase
   * @param store       the store to purchase the product from
   * @param id          the id of this bot
   * @param boughtSoFar the amount of products nought so far
   * @param maxToBuy    the max number of products to buy
   */
  public ShopBot(Product product, IStore store, int id, AtomicInteger boughtSoFar, int maxToBuy) {
    this(product, store, id, true);
    this.boughtSoFar = boughtSoFar;
    this.maxToBuy = maxToBuy;
  }

  /**
   * Convenience constructor that initializes a {@code ShopBot} with a specific id and in headless
   * mode or not. It also stores a reference * to the amount bought so far as well as the max amount
   * of products to buy.
   *
   * @param product     the product to purchase
   * @param store       the store to purchase the product from
   * @param id          the id of this bot
   * @param headless    if it shoudl be started in headless mode
   * @param boughtSoFar the amount of products nought so far
   * @param maxToBuy    the max number of products to buy
   */
  public ShopBot(Product product, IStore store, int id, boolean headless,
      AtomicInteger boughtSoFar,
      int maxToBuy) {
    this(product, store, id, boughtSoFar, maxToBuy);
    this.headless = headless;
  }

  /**
   * Initializes the a headless chrome driver.
   */
  private void initDriver() {
    if (this.headless) {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
          "--ignore-certificate-errors");
      this.driver = new ChromeDriver(options);
    } else {
      this.driver = new ChromeDriver();
    }
  }

  @Override
  public void run() {
    this.initDriver();
    System.out.println("Started shop bot of id '" + this.id + "'");
    try {
      this.store.purchaseProduct(product, this.driver, this.boughtSoFar, this.maxToBuy);
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }
}

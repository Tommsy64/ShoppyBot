package model.product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import model.shoppybot.ShoppyBot;
import model.store.IStore;

/**
 * Represents a product that the user wants to purchase.
 */
public class Product {

  public final String name;
  public final int quantity;
  private String url;
  private ExecutorService executor;
  private AtomicInteger purchased;

  /**
   * Constructs a product of given name and desired quantity. The url is optional, and it's a direct
   * link to the product's store page.
   *
   * @param name     the name of the product
   * @param quantity the quantity of the product
   * @param url      the url to the product's store page
   */
  public Product(String name, int quantity, String url) {
    if (name == null) {
      throw new IllegalArgumentException("Name of product must not be null.");
    }
    this.name = name;
    this.quantity = quantity;
    this.url = url;
    this.purchased = new AtomicInteger(0);
  }

  /**
   * Tries to purchase this product across the list of stores.
   *
   * @param stores the list of stores to try to purchase from
   */
  public void purchase(List<IStore> stores) {
    this.executor = Executors.newFixedThreadPool(stores.size());
    this.startShoppyBots(stores);
  }


  /**
   * Starts a bot for store we're trying to purchase this product from.
   */
  private void startShoppyBots(List<IStore> stores) {
    for (IStore store : stores) {
      this.executor.submit(new ShoppyBot(this, store));
    }
  }

  /**
   * Should this product be purchased?
   *
   * @return {@code true} if the amount purchased is less than the amount the user wants to purchase
   */
  public boolean shouldStillPurchase() {
    return purchased.get() < this.quantity;
  }

  /**
   * Increments the total 'purchased' of this product.
   *
   * @param quantity how many of this product were purchased
   */
  public void purchased(int quantity) {
    this.purchased.set(this.purchased.get() + quantity);
  }

  public String getUrl() {
    return this.url;
  }

  @Override
  public String toString() {
    return this.name;
  }
}

package model.bot;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import model.product.Product;
import model.store.IStore;

/**
 * An object that will try to buy any n of the products given.
 */
public class ShopBotBuilder {

  private Map<Product, IStore> products;
  private AtomicInteger boughtSoFar;

  /**
   * Constructor that stores the products to buy.
   *
   * @param products
   */
  public ShopBotBuilder(Map<Product, IStore> products) {
    this.products = products;
    this.boughtSoFar = new AtomicInteger(0);
  }

  /**
   * Tries to buy any n of the given products.
   *
   * @param maxToBuy the max number of products to buy
   */
  public void tryToBuy(int maxToBuy) {
    int i = 0;
    for (Product product : this.products.keySet()) {
      new ShopBot(product, products.get(product), i, this.boughtSoFar, maxToBuy);
      i++;
    }
  }
}

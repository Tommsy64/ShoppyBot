package model.bot;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import model.product.ProductOfStore;

/**
 * An object that will try to buy any n of the products given.
 */
public class ShopBotBuilder implements Runnable {

  private List<ProductOfStore> products;
  private AtomicInteger boughtSoFar;
  private ExecutorService executor;
  private int maxToBuy;
  private boolean headless;

  /**
   * Constructor that stores the products to buy.
   *
   * @param products the products to buy
   * @param maxToBuy the max nmber of products to buy
   */
  public ShopBotBuilder(List<ProductOfStore> products, int maxToBuy) {
    this.products = products;
    this.boughtSoFar = new AtomicInteger(0);
    this.executor = Executors.newFixedThreadPool(products.size());
    this.maxToBuy = maxToBuy;
    this.headless = true;
  }

  /**
   * Debugging constructor that stores the products to buy as well as if it's in headless mode or
   * not.
   *
   * @param products the products to buy
   * @param maxToBuy the max nmber of products to buy
   * @param headless if the chrome driver is in headless mode or not
   */
  public ShopBotBuilder(List<ProductOfStore> products, int maxToBuy, boolean headless) {
    this(products, maxToBuy);
    this.headless = headless;
  }


  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method <code>run</code> is that it may take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    this.startAllBots();

    // all submitted commands will be executed before
    // stopping the ExecutorService, but no new command is accepted.
    this.executor.shutdown();
  }

  /**
   * Starts a bot for every product that the user is trying to buy.
   */
  private void startAllBots() {
    for (int i = 0; i < this.products.size(); i++) {
      ProductOfStore ps = this.products.get(i);
      this.executor.submit(new ShopBot(ps.product, ps.store, i, this.headless, this.boughtSoFar,
          this.maxToBuy));
    }
  }
}

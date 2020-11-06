package model.store;

import java.util.concurrent.atomic.AtomicInteger;
import model.product.Product;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents the online store 'BestBuy'.
 */
public class Amazon extends AStore {

  /**
   * Default constructor that initializes the xPathToStore.
   */
  public Amazon() {
    super("//input[@id='add-to-cart-button']/.././span");
  }

  /**
   * Initializes the product library by adding products and the urls to them.
   */
  @Override
  protected void fillProductLibrary() {
    this.productLibrary
        .put(Product.FIRE_TV_STICK,
            "https://www.amazon.com/Fire-TV-Stick-4K-with-Alexa-Voice-Remote/dp/B079QHML21/ref"
                + "=zg_bs_electronics_home_2?_encoding=UTF8&psc=1&refRID=WTDFTB95DGRPXVDVG400");
  }

  /**
   * Purchases the product that this store is currently viewing if the number of products bougth so
   * far is less than the max.
   *
   * @param driver      the chrome driver
   * @param boughtSoFar how many have been bought so far
   * @param maxToBuy    the max number of products to buy
   */
  @Override
  protected void purchaseCurrentProduct(ChromeDriver driver, AtomicInteger boughtSoFar,
      int maxToBuy) {
    // TODO -- add card payment stuff here
    if (this.canStillBuy(boughtSoFar, maxToBuy)) {
      // TODO -- Finalize purchase
      boughtSoFar.incrementAndGet();
    }
  }
}

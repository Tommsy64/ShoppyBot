package model.store;

import java.util.concurrent.atomic.AtomicInteger;
import model.product.Product;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents the online store 'BestBuy'.
 */
public class BestBuy extends AStore {

  /**
   * Default constructor that initializes the xPathToStore.
   */
  public BestBuy() {
    super("//*[contains(@id,'fulfillment-add-to-cart-button')]");
  }

  /**
   * Initializes the product library by adding products and the urls to them.
   */
  @Override
  protected void fillProductLibrary() {
    this.productLibrary
        .put(Product.NVIDIA_GEFORCE_RTX3080, "https://www.bestbuy.com/site/nvidia-geforce-rtx-3080"
            + "-10gb-gddr6x-pci-express-4-0-graphics-card-titanium-and-black/6429440"
            + ".p?skuId=6429440");
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

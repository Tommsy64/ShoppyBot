package model.store;

import java.util.concurrent.atomic.AtomicInteger;
import model.product.Product;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents the online store 'BestBuy'.
 */
public class NewEgg extends AStore {

  /**
   * Default constructor that initializes the xPathToStore.
   */
  public NewEgg() {
    super("//button[contains(text(),'Add to cart') and contains(@class,'btn-wide')]");
  }

  /**
   * Initializes the product library by adding products and the urls to them.
   */
  @Override
  protected void fillProductLibrary() {
    this.productLibrary.put(Product.NVIDIA_GEFORCE_RTX3080,
        "https://www.newegg.com/asus-geforce-rtx-3080-tuf-rtx3080-10g-gaming/p/N82E16814126453"
            + "?cm_mmc=vendor-nvidia");

    this.productLibrary.put(Product.ASUS_TUF_RTX3080,
        "https://www.newegg.com/asus-geforce-rtx-3080-tuf-rtx3080-10g-gaming/p/N82E16814126453"
            + "?cm_mmc=vendor-nvidia");

    this.productLibrary.put(Product.ZOTAC_GEFORCE_RTX3080, "https://www.newegg"
        + ".com/zotac-geforce-rtx-3080-zt-a30800d-10p/p/N82E16814500502?cm_mmc=vendor-nvidia");

    this.productLibrary.put(Product.MSI_GEFORCE_RTX3080,
        "https://www.newegg.com/msi-geforce-rtx-3080-rtx-3080-ventus-3x-10g/p/N82E16814137600"
            + "?cm_mmc=vendor-nvidia");
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

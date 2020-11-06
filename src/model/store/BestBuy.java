package model.store;

import model.product.Product;

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
    this.productLibrary.put(Product.RTX3080, "https://www.bestbuy.com/site/nvidia-geforce-rtx-3080"
        + "-10gb-gddr6x-pci-express-4-0-graphics-card-titanium-and-black/6429440.p?skuId=6429440");
  }

  /**
   * Purchases the product that this store is currently viewing.
   */
  @Override
  protected void purchaseCurrentProduct() {
    // TODO
  }
}

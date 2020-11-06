package model.product;

import model.store.IStore;

/**
 * This object couples a product to a store.
 */
public final class ProductOfStore {

  public final Product product;
  public final IStore store;

  /**
   * Stores the given product and store.
   *
   * @param product the product to store
   * @param store   the store to store (lol)
   */
  public ProductOfStore(Product product, IStore store) {
    this.product = product;
    this.store = store;
  }
}

package model.store;

import java.util.HashMap;
import java.util.Map;
import model.product.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents an abstract store.
 */
public abstract class AStore implements IStore {

  // the XPath to the checkout button
  private final String xPathToCheckout;
  // a map of products to their URLs
  protected Map<Product, String> productLibrary;

  /**
   * Constructor that assigns the XPathToCheckout.
   *
   * @param xPathToCheckout the XPath to the checkout button
   */
  public AStore(String xPathToCheckout) {
    this.xPathToCheckout = xPathToCheckout;
    this.productLibrary = new HashMap<>();
    this.fillProductLibrary();
  }

  /**
   * Initializes the product library by adding products and the urls to them.
   */
  protected abstract void fillProductLibrary();

  /**
   * Purchases the product that this store is currently viewing.
   */
  protected abstract void purchaseCurrentProduct();

  /**
   * Returns {@code true} if the product is in stock.
   *
   * @param product the product to check
   * @param driver  the browser driver to check if the product is in stock
   * @return {@code true} if the product is in stock
   * @throws IllegalStateException    thrown when the checkout button says something unexpected
   * @throws IllegalArgumentException thrown when the given product is not offered by the store
   */
  @Override
  public boolean isInStock(Product product, ChromeDriver driver) throws IllegalStateException,
      IllegalArgumentException {
    String productUrl = this.productLibrary.get(product);

    if (productUrl == null) {
      throw new IllegalArgumentException("The given product is not offered by this store.");
    }

    driver.get(productUrl);
    WebElement checkoutButton = driver.findElementByXPath(this.xPathToCheckout);
    String innerText = checkoutButton.getAttribute("innerText").toLowerCase();
    if (innerText.contains("sold out") || innerText.contains("notify") || innerText.contains(
        "notification")) {
      return false;
    } else if (innerText.contains("add to cart")) {
      return true;
    } else {
      throw new IllegalStateException("Could not determine if the item was in stock. The button "
          + "said '" + innerText + "'");
    }
  }

  /**
   * Purchases the given product.
   *
   * @param product the product to purchase
   * @param driver
   * @throws IllegalStateException    thrown when the product is out of stock or something
   *                                  unexpected happened.
   * @throws IllegalArgumentException thrown when the given product is not offered by the store
   */
  @Override
  public void purchaseProduct(Product product, ChromeDriver driver)
      throws IllegalStateException, IllegalArgumentException {
    if (!this.isInStock(product, driver)) {
      throw new IllegalStateException("The product was not in stock.");
    }
    this.purchaseCurrentProduct();
  }
}

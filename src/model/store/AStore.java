package model.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import model.product.Product;
import org.openqa.selenium.NoSuchElementException;
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
   * Purchases the product that this store is currently viewing if the number of products bougth so
   * far is less than the max.
   *
   * @param driver      the chrome driver
   * @param boughtSoFar how many have been bought so far
   * @param maxToBuy    the max number of products to buy
   */
  protected abstract void purchaseCurrentProduct(ChromeDriver driver, AtomicInteger boughtSoFar,
      int maxToBuy);

  /**
   * Returns a list of products sold at this store.
   *
   * @return the list of products sold
   */
  @Override
  public List<Product> getSoldProducts() {
    return new ArrayList<>(this.productLibrary.keySet());
  }

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
    WebElement checkoutButton;

    try {
      checkoutButton = driver.findElementByXPath(this.xPathToCheckout);
    } catch (NoSuchElementException e) {
      return false;
    }

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
   * Purchases the given product if the total bought so far is less than the max number to buy.
   *
   * @param product     the product to purchase
   * @param driver      the chrome driver
   * @param boughtSoFar how many products have been bought so far
   * @param maxToBuy    the max number of products to buy
   * @throws IllegalStateException    thrown when something unexpected happened.
   * @throws IllegalArgumentException thrown when the given product is not offered by the store
   */
  @Override
  public void purchaseProduct(Product product, ChromeDriver driver, AtomicInteger boughtSoFar,
      int maxToBuy)
      throws IllegalStateException, IllegalArgumentException {
    if (this.canStillBuy(boughtSoFar, maxToBuy)) {
      try {
        this.verifyProductIsInStock(product, driver);
        this.purchaseCurrentProduct(driver, boughtSoFar, maxToBuy);
        System.out.println("The " + product.toString() + " was successfully purchased!");

        // try to buy another after buying one
        // TODO -- instead of doing this, we'd just select the quantity when doing checkout
        this.purchaseProduct(product, driver, boughtSoFar, maxToBuy);
      } catch (IllegalStateException e) {
        if (e.getMessage().equals("The " + product.toString() + " was not in stock.")) {
          System.out.println("The " + product.toString() + " was out of stock...Retrying!");
          this.purchaseProduct(product, driver, boughtSoFar, maxToBuy);
        } else {
          System.out.println(e.getMessage());
          throw e;
        }
      }
    } else {
      driver.close();
    }
  }

  /**
   * Verifies the given product is in stock.
   *
   * @param product the product
   * @param driver  the chrome driver
   * @throws IllegalStateException when the product was not in stock
   */
  private void verifyProductIsInStock(Product product, ChromeDriver driver) {
    if (!this.isInStock(product, driver)) {
      throw new IllegalStateException("The " + product.toString() + " was not in stock.");
    }
  }

  /**
   * Is the number of products bought so far less than the max?
   *
   * @param boughtSoFar total bought so far
   * @param maxToBuy    max to buy
   * @return {@code true} if bought so far is less than the max
   */
  protected boolean canStillBuy(AtomicInteger boughtSoFar,
      int maxToBuy) {
    return boughtSoFar.get() < maxToBuy;
  }
}

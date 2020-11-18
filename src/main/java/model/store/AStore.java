package model.store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import model.product.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents an abstract store.
 */
public abstract class AStore implements IStore {

  protected final String searchURL;
  // the XPath to the checkout button
  private final String xPathToCheckout;

  /**
   * Constructor that assigns the XPathToCheckout.
   *
   * @param xPathToCheckout the XPath to the checkout button
   * @param searchURL       url to search for products
   */
  public AStore(String xPathToCheckout, String searchURL) {
    this.xPathToCheckout = xPathToCheckout;
    this.searchURL = searchURL;
  }

  /**
   * Purchases the given product if the total bought so far is less than the max number to buy.
   *
   * @param product the product to purchase
   * @param driver  the chrome driver
   * @throws IllegalStateException    thrown when something unexpected happened.
   * @throws IllegalArgumentException thrown when the given product is not offered by the store
   */
  @Override
  public void purchaseProduct(Product product, ChromeDriver driver)
      throws IllegalStateException, IllegalArgumentException {
    if (product.shouldStillPurchase()) {
      if (this.isInStock(product, driver)) {
        this.purchaseCurrentProduct(driver, product.quantity);
        product.purchased(product.quantity);
      } else {
        System.out.println("The " + product.toString() + " was out of stock...Retrying!");
        this.purchaseProduct(product, driver);
      }
    } else {
      driver.close();
    }
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
  private boolean isInStock(Product product, ChromeDriver driver) throws IllegalStateException,
      IllegalArgumentException {

    this.goToProductPage(product, driver);

    return this.canAddToCart(driver.findElementByXPath(this.xPathToCheckout));
  }

  /**
   * Goes to the product page for the given product
   *
   * @param product the product
   * @param driver  the browser driver
   */
  private void goToProductPage(Product product, ChromeDriver driver) {
    String productUrl = product.getUrl();

    if (productUrl == null) {
      this.searchForProduct(product, driver);
    } else {
      driver.get(productUrl);
    }
  }

  /**
   * Searches the store for the given product and goes to the first result that contains the name of
   * the product in its title.
   *
   * @param product the product to search for
   * @param driver  the browser driver
   */
  protected void searchForProduct(Product product, ChromeDriver driver) {
    try {
      driver
          .get(this.searchURL + URLEncoder.encode(product.name, StandardCharsets.UTF_8.toString()));
      this.selectProductFromSearchResults(product, driver);
    } catch (UnsupportedEncodingException e) {
      System.out.println("Could not search for the product '" + product.name + "' due to URL "
          + "encoding error.");
      e.printStackTrace();
    }
  }

  /**
   * Selects the product from the list of search results in the current page.
   *
   * @param product the product that is being searched for
   * @param driver  the browser driver
   */
  protected abstract void selectProductFromSearchResults(Product product, ChromeDriver driver);


  protected boolean resultIsProduct(WebElement result, Product product) {
    String innerText = result.getAttribute("innerText");

    for (String word : product.name.split(" ")) {
      if (!innerText.contains(word)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Does the checkout button say 'Add to cart' on it?
   *
   * @param checkoutButton the checkout button
   * @return {@code true} if it says 'Add to cart'
   * @throws IllegalStateException thrown when unexpected text is found in the checkoutButton
   */
  private boolean canAddToCart(WebElement checkoutButton) throws IllegalStateException {
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
   * Purchases the product that this store is currently viewing if the number of products bought so
   * far is less than the max.
   *
   * @param driver   the chrome driver
   * @param quantity the number of products to buy
   */
  protected abstract void purchaseCurrentProduct(ChromeDriver driver, int quantity);


  /**
   * Returns a duple of username and password from the credentials.txt file.
   *
   * @return a duple of username and password
   */
  protected String[] getCredentials() throws IOException {
    String storeName = this.getStoreName();
    String credentials[] = new String[2];

    BufferedReader reader = new BufferedReader(new FileReader("./credentials.txt"));
    String line = reader.readLine();

    while (line != null) {
      // if the credentials haven't been found yet...
      if (credentials[0] == null || credentials[1] == null) {
        // and the line contains the store name...
        if (line.contains(storeName)) {
          // get the username or password
          if (line.contains("username")) {
            credentials[0] = line.substring(line.indexOf(":") + 1);
          } else if (line.contains("password")) {
            credentials[1] = line.substring(line.indexOf(":") + 1);
          }
        }
        line = reader.readLine();
      } else {
        break;
      }
    }

    reader.close();

    return credentials;
  }

  /**
   * Returns the name of the store in all lowercase (to fetch it from the credentials.txt file)
   *
   * @return the name of the store (in all lower case)
   */
  protected abstract String getStoreName();
}

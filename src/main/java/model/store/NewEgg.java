package model.store;

import java.io.IOException;
import model.product.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Represents the online store 'BestBuy'.
 */
public class NewEgg extends AStore {

  /**
   * Default constructor that initializes the xPathToStore.
   */
  public NewEgg() {
    super("//button[contains(text(),'Add to cart') and contains(@class,'btn-wide')]",
        "https://www.newegg.com/p/pl?d=");
  }

  /**
   * Selects the product from the list of search results in the current page.
   *
   * @param product the product that is being searched for
   * @param driver  the browser driver
   */
  @Override
  protected void selectProductFromSearchResults(Product product, ChromeDriver driver) {
  }

  /**
   * Purchases the product that this store is currently viewing if the number of products bought so
   * far is less than the max.
   *
   * @param driver   the chrome driver
   * @param quantity the number of products to buy
   */
  @Override
  protected void purchaseCurrentProduct(ChromeDriver driver, int quantity) {
  }

  /**
   * Logs into the online store account (for purchases later).
   */
  @Override
  public void login(ChromeDriver driver) throws IOException {
    // TODO -- finish this...
    System.out.println("Logging into newegg...");

    driver
        .get(
            "https://www.newegg.com/");

    String[] credentials = this.getCredentials();

    driver.findElementByXPath("/html/body/div[9]/header/div[1]/div[4]/div[1]/div[1]").click();

    WebElement emailInput = driver
        .findElementByXPath(
            "/html/body/div[5]/div/div[2]/div/div/div[1]/form/div/div[1]/div/input");
    emailInput.sendKeys(credentials[0]);

    driver.findElementByXPath("/html/body/div[5]/div/div[2]/div/div/div[1]/form/div/div[3]")
        .click();

    driver
        .findElementByXPath("/html/body/div[5]/div/div[2]/div/div/div[2]/form/div/div[2]/div/input")
        .sendKeys(credentials[1]);

    driver.findElementByXPath("/html/body/div[5]/div/div[2]/div/div/div[2]/form/div/div[3]/button")
        .click();

    System.out.println("Logged into newegg.");
  }

  /**
   * Returns the name of the store in all lowercase (to fetch it from the credentials.txt file)
   *
   * @return the name of the store (in all lower case)
   */
  @Override
  protected String getStoreName() {
    return "newegg";
  }

}

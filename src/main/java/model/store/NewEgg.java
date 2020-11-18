package model.store;

import java.io.IOException;
import java.util.List;
import model.product.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    List<WebElement> results = driver.findElements(
        By.xpath("//div[contains(@class,'item-cell')]/div[contains(@class,'item-container')]"));

    for (WebElement result : results) {
      if (this.resultIsProduct(result, product)) {
        this.clickOnSearchResult(driver, result);
        return;
      }
    }

    System.out.println(String.format("Couldn't find '%s'...", product.name));
    System.out.println("Trying again...");
    this.searchForProduct(product, driver);

  }


  /**
   * Clicks on a searh result to go to its product page
   *
   * @param result the search result
   */
  private void clickOnSearchResult(WebDriver driver, WebElement result) {
    WebElement title = result.findElement(By.tagName("a"));
    String href = title.getAttribute("href");
    driver.get(href);
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
    System.out.println("Logging into newegg...");

    driver
        .get(
            "https://www.newegg.com/");

    WebDriverWait wait = new WebDriverWait(driver, 1800);
    wait.until(ExpectedConditions.urlContains("NewMyAccount"));

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

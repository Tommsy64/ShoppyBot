package io.github.lennyparisineu.shoppybot.store;

import io.github.lennyparisineu.shoppybot.product.Product;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Represents the online store 'BestBuy'.
 */
public class Amazon extends AStore {

  /**
   * Default constructor that initializes the xPathToStore.
   */
  public Amazon() {
    super("//input[@id='add-to-cart-button']/.././span", "https://www.amazon.com/s?k=");
  }


  /**
   * Selects the product from the list of search results in the current page.
   *
   * @param product the product that is being searched for
   * @param driver  the browser driver
   */
  @Override
  protected void selectProductFromSearchResults(Product product, ChromeDriver driver) {
    List<WebElement> results = driver.findElementsByXPath("//div[contains(@data-component-type,"
        + "'s-search-result')]");

    for (WebElement result : results) {
      if (this.resultIsProduct(result, product)) {
        this.clickOnSearchResult(driver, result);
        return;
      }
    }

    System.out.println(String.format("Couldn't find '%s'... Trying again...", product.name));
    this.searchForProduct(product, driver);
  }


  /**
   * Clicks on a searh result to go to its product page
   *
   * @param result the search result
   */
  private void clickOnSearchResult(WebDriver driver, WebElement result) {
    WebElement title = result.findElement(By.tagName("h2"));
    WebElement aTag = title.findElement(By.xpath("./a"));
    String href = aTag.getAttribute("href");
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

    try {
      driver.findElement(By.id("buy-now-button")).click();
      driver.findElement(By.id("turbo-checkout-pyo-button")).click();
    } catch (NoSuchElementException e) {
      List<WebElement> checkoutButtons = driver.findElements(By.name("placeYourOrder1"));
      checkoutButtons.get(0).click();
    }
  }


  /**
   * Logs into the online store account (for purchases later).
   */
  @Override
  public void login(ChromeDriver driver) throws IOException {
    System.out.println("Logging into amazon...");

    driver.get(
        "https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid"
            + ".return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_custrec_signin&openid"
            + ".identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid"
            + ".assoc_handle=usflex&openid.mode=checkid_setup&openid"
            + ".claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid"
            + ".ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&");
    String[] credentials = this.getCredentials();

    WebElement emailInput = driver
        .findElementByXPath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div[1]/form/div/div/div"
            + "/div[1]/input[1]");
    emailInput.sendKeys(credentials[0]);

    driver.findElementByXPath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div[1]/form/div/div"
        + "/div/div[2]/span/span/input").click();

    driver.findElementByXPath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div/form/div"
        + "/div[1]/input").sendKeys(credentials[1]);

    driver.findElementByXPath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div/form/div"
        + "/div[2]/span/span/input").click();

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo")));

    System.out.println("Logged into amazon.");
  }

  /**
   * Returns the name of the store in all lowercase (to fetch it from the credentials.txt file)
   *
   * @return the name of the store (in all lower case)
   */
  @Override
  protected String getStoreName() {
    return "amazon";
  }

}

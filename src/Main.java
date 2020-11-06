import model.product.Product;
import model.store.BestBuy;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

  public static void main(String[] args) {
    // Create a new chrome driver
    ChromeDriver driver = new ChromeDriver();

    // Create a new instance of BestBuy
    BestBuy bestBuy = new BestBuy();

    // Check if the RTX3080 is in stock
    if (bestBuy.isInStock(Product.RTX3080, driver)) {
      System.out.println("The RTX 3080 is now in stock!");
    } else {
      System.out.println("The RTX 3080 is not in stock!");
    }
  }
}
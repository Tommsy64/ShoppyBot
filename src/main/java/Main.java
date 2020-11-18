import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.product.Product;
import model.store.Amazon;
import model.store.IStore;

public class Main {

  public static void main(String[] args) throws IOException {
    String productName = null;
    int quantity = 1;
    String url = null;

    for (int i = 0; i < args.length; i += 2) {
      switch (args[i]) {
        case "-name":
          productName = args[i + 1];
          break;
        case "-quantity":
          try {
            quantity = Integer.parseInt(args[i + 1]);
            break;
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity must be an integer.");
          }
        case "-url":
          url = args[i + 1];
          break;
      }
    }

    Product product = new Product(productName, quantity, url);
    purchaseFromAllStores(product);
  }

  private static void purchaseFromAllStores(Product product) {
    List<IStore> stores = new ArrayList<>();
    stores.add(new Amazon());
//    stores.add(new NewEgg());
    product.purchase(stores);
  }
}
import java.util.ArrayList;
import java.util.List;
import model.bot.ShopBotBuilder;
import model.product.Product;
import model.product.ProductOfStore;
import model.store.Amazon;
import model.store.IStore;
import model.store.NewEgg;

public class Main {

  public static void main(String[] args) {
    startStoreWideBot(new Amazon(), 3, false);
    startStoreWideBot(new NewEgg(), 2, true);
  }

  /**
   * Sample method that demonstrates how one would use these classes to buy all products from a
   * store.
   *
   * @param store      the store to purchase from
   * @param totalToBuy total number of products to buy
   * @param headless   if the bots should be headless
   */
  private static void startStoreWideBot(IStore store, int totalToBuy, boolean headless) {
    List<ProductOfStore> newEggProducts = new ArrayList<>();
    for (Product p : store.getSoldProducts()) {
      newEggProducts.add(new ProductOfStore(p, store));
    }
    ShopBotBuilder storeWideBot = new ShopBotBuilder(newEggProducts, totalToBuy, headless);
    storeWideBot.run();
  }
}
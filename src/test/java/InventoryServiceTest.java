/*

тесты:

1 - добавление товара на склад, если inventoryOpen = true
2 - добавление товара на склад, если inventoryOpen = false -> OutOfStockException

3 - получение товара по категории, товар и категория присутствуют, inventoryOpen = true
4 - получение товара по категории, категория присутствует, товар - нет, inventoryOpen = true
5 - получение товара по категории, категория отсутствует, inventoryOpen = true
6 - получение товара по категории, товар и категория присутствуют, inventoryOpen = false

7 - фильтрация товара по категории, категория присутствует, есть продукты
8 - фильтрация товара по категории, категория присутствует, продуктов нет
9 - фильтрация товара по категории, категория отсутствует

10 - фильтрация товаров по цене, подходящие товары в разных категориях
11 - фильтрация товаров по цене, подходящих товаров по цене нет


 */

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import task5.InventoryService;
import task5.OutOfStockException;
import task5.Product;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {

    // 1 - добавление товара на склад, если inventoryOpen = true

    @Test
    public void testUserCanAddProduct() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);

        inventoryService1.addProduct(product1);

        // проверяем, что после добавления у нас создалась одна пара entry
        assertEquals(1, inventoryService1.getInventoryMap().size());

        // проверяем, что после добавления в списке продуктов данной категории - один продукт
        assertEquals(1, inventoryService1.getInventoryMap().get(product1.getCategory()).size());

        // проверяем, что после добавления в мапе есть entry с ключом - добавленного продукта
        assertTrue(inventoryService1.getInventoryMap().containsKey(product1.getCategory()));

        // проверяем, что после добавления в мапе есть добавленный продукт
        assertTrue(inventoryService1.getInventoryMap().get(product1.getCategory()).contains(product1));
    }

    // 2 - добавление товара на склад, если inventoryOpen = false -> OutOfStockException
    // (инвентаризацию не открыли)

    @Test
    public void testUserCanNotAddProductInventoryClosed() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();

        assertThrows(OutOfStockException.class, () -> inventoryService1.addProduct(product1));
    }

    //  2 - добавление товара на склад, если inventoryOpen = false -> OutOfStockException
    //  (инвентаризацию открыли, а затем закрыли)

    @Test
    public void testUserCanNotAddProductInventoryOpenThenClosed() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);
        inventoryService1.setInventoryOpen(false);

        assertThrows(OutOfStockException.class, () -> inventoryService1.addProduct(product1));
    }


    // 3 - получение товара по категории, товар и категория присутствуют, inventoryOpen = true

    @Test
    public void testGetProductByCategory() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);
        inventoryService1.addProduct(product1);

        // проверяем, что получили ожидаемый продукт
        assertEquals(product1, inventoryService1.getProductByCategory(product1.getCategory()));

        // проверяем, что продукта нет в списке по данной категории
        assertTrue(inventoryService1.getInventoryMap().get(product1.getCategory()).isEmpty());
    }


    // 4 - получение товара по категории, категория присутствует, товар - нет, inventoryOpen = true
    @Test
    public void testGetProductByCategoryNoProducts() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);
        inventoryService1.addProduct(product1);

        inventoryService1.getProductByCategory(product1.getCategory());

        assertThrows(OutOfStockException.class, () -> inventoryService1.getProductByCategory(product1.getCategory()));
    }


    // 5 - получение товара по категории, категория отсутствует, inventoryOpen = true
    @Test
    public void testGetProductNoCategory() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);
        inventoryService1.addProduct(product1);

        assertThrows(OutOfStockException.class, () -> inventoryService1.getProductByCategory("Техника"));
    }

    // 6 - получение товара по категории, товар и категория присутствуют, инвентаризацию закрыли после добавления продукта
    @Test
    public void testGetProductInventoryClosed() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);
        inventoryService1.addProduct(product1);
        inventoryService1.setInventoryOpen(false);

        assertThrows(OutOfStockException.class, () -> inventoryService1.getProductByCategory(product1.getCategory()));

    }

    // 7 - фильтрация товара по категории, категория присутствует, есть продукты

    @Test
    public void testFilterByCategory() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        Product product2 = new Product("Шампунь", 300.0, "Косметика");
        Product product3 = new Product("Чайник", 4000.0, "Техника");

        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);

        inventoryService1.addProduct(product1);
        inventoryService1.addProduct(product2);
        inventoryService1.addProduct(product3);

        // проверяем, что в полученном списке продуктов категории ожидаемое число элементов
        assertEquals(2, inventoryService1.getListOfProductsFilteredByCategory(product1.getCategory()).size());

        // проверяем, что имя первого продукта из полученного отфильтрованного списка = ожидаемому
        assertEquals(product1.getName(),
                inventoryService1.getListOfProductsFilteredByCategory(product1.getCategory()).getFirst().getName());

        // проверяем, что цена второго продукта из полученного отфильтрованного списка = ожидаемому
        assertEquals(product2.getPrice(),
                inventoryService1.getListOfProductsFilteredByCategory(product1.getCategory()).getLast().getPrice());

    }

    // 8 - фильтрация товара по категории, категория присутствует, продуктов нет

    @Test
    public void testFilterByCategoryNoProducts() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);
        inventoryService1.addProduct(product1);
        inventoryService1.getProductByCategory(product1.getCategory());

        assertThrows(OutOfStockException.class,
                () -> inventoryService1.getListOfProductsFilteredByCategory(product1.getCategory()));

    }


    // 9 - фильтрация товара по категории, категория отсутствует
    @Test
    public void testFilterNoCategory() {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);
        inventoryService1.addProduct(product1);

        assertThrows(OutOfStockException.class,
                () -> inventoryService1.getListOfProductsFilteredByCategory("Техника"));

    }


    // 10 - фильтрация товаров по цене, подходящие товары в разных категориях
    // 11 - фильтрация товаров по цене, подходящих товаров по цене нет

    @ParameterizedTest
    @CsvSource({
            "300.0, 3",
            "500.0, 2",
            "5000.0, 0"
    })
    public void testFilterByPrice(double price, int listSize) {
        Product product1 = new Product("Крем", 1000.0, "Косметика");
        Product product2 = new Product("Шампунь", 300.0, "Косметика");
        Product product3 = new Product("Чайник", 4000.0, "Техника");

        InventoryService inventoryService1 = new InventoryService();
        inventoryService1.setInventoryOpen(true);

        inventoryService1.addProduct(product1);
        inventoryService1.addProduct(product2);
        inventoryService1.addProduct(product3);

        // проверяем размер полученного списка равен ожидаемому значению
        assertEquals(listSize, inventoryService1.getListOfProductFilteredByPrice(price).size());
    }








}



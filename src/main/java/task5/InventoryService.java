package task5;


// InventoryService: Сервис для управления товарами на складе. Должен поддерживать операции добавления товара и извлечения товара по категории.
// Управление товарными запасами:
// Хранение товаров осуществляется в структуре Map<String, List<Product>>, где ключ - это категория товара.
// Метод для добавления товара на склад. Если флаг isInventoryOpen равен false, операция добавления не должна выполняться.
// Метод для получения товара по категории. Если в указанной категории нет товаров, должно выбрасываться исключение OutOfStockException.
// Работа с данными:
// Использование Stream API для поиска и фильтрации товаров по категориям.
// Фильтрация товаров по цене с использованием лямбда-выражений.

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryService {

    // Флаг по управлению инвентаризацией.
    private boolean isInventoryOpen;


    // Хранение товаров осуществляется в структуре Map<String, List<Product>>, где ключ - это категория товара.
    private final Map<String, List<Product>> inventoryMap = new ConcurrentHashMap<>();

    // Установка значения флага isInventoryOpen.
    public void setInventoryOpen (boolean isInventoryOpen) {
        this.isInventoryOpen = isInventoryOpen;
    }

    // Метод для добавления товара на склад. Если флаг isInventoryOpen равен false, операция добавления не должна выполняться.

    public void addProduct(Product product) {

        if (!isInventoryOpen) {
            throw new OutOfStockException("Инвентаризация закрыта!");
        }

        inventoryMap.computeIfAbsent(product.getCategory(), key -> new ArrayList<>()).add(product);
    }


    // Метод для получения товара по категории. Если в указанной категории нет товаров, должно выбрасываться
    // исключение OutOfStockException.

    public Product getProductByCategory(String category) {

        if (!isInventoryOpen) {
            throw new OutOfStockException("Инвентаризация закрыта!");
        }

        if (!inventoryMap.containsKey(category)) {
            throw new OutOfStockException("Отсутствует данная категория!");
        }

        List<Product> listOfProductOfCategory = inventoryMap.get(category);


        if (listOfProductOfCategory == null || listOfProductOfCategory.isEmpty()) {
            throw new OutOfStockException("Отсутствуют продукты в данной категории!");
        }

        return listOfProductOfCategory.removeLast();
    }

    // Использование Stream API для поиска и фильтрации товаров по категориям
    // Вместо этого используется получение значения по ключу
    // Допустим, что фильтровать товары можно, если
    // инвентаризация закрыта

    public List<Product> getListOfProductsFilteredByCategory(String category) {

        if (!inventoryMap.containsKey(category)) {
            throw new OutOfStockException("Отсутствует данная категория!");
        }

        List<Product> listOfProductOfCategory = inventoryMap.get(category);

        if (listOfProductOfCategory == null || listOfProductOfCategory.isEmpty()) {
            throw new OutOfStockException("Отсутствуют продукты в данной категории!");
        }

        return inventoryMap.get(category);
    }


    // Фильтрация товаров по цене с использованием лямбда-выражений. Допустим, что фильтровать товары можно, если
    // инвентаризация закрыта

    public List<Product> getListOfProductFilteredByPrice(double minPrice) {
        return inventoryMap.values().stream()
                .flatMap(List::stream)
                .filter(product -> product.getPrice() >= minPrice)
                .toList();
    }

    public Map<String, List<Product>> getInventoryMap() {
        return Map.copyOf(inventoryMap);
    }

}

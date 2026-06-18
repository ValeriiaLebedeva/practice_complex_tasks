/*
Тест-кейсы:

1 - happy path: добавление объекта в пустую коллекцию - ожидаемый результат: объект в коллекции, размер коллекции 0->1
2 - тест - переопределен ли equals
3 - тест - переопределен ли hashcode
4 - тест - добавление null - допустим, по требованиям, мы ожидаем, что должно выбрасываться IllegalArgumentException
5 - тест - удаление элемента из коллекции -> Возвращает true, если объект был удалён + проверяем, что объект отсутствует в
списке
6 - тест - удаление элемента из коллекции -> Возвращает false, если объект не найден в коллекции
7 - happy path: метод возвращает ожидаемый список пользователей в заданном возрастном диапазоне
8 - happy path: метод возвращает ожидаемый список пользователей, чьи имена соответствуют заданной строке.
9 - happy path: метод возвращает ожидаемый список пользователей с заданным статусом активности

 */

import org.junit.jupiter.api.Test;
import task1.EntityManager;
import task1.User;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerTest {


    // 1 - happy path: добавление объекта в пустую коллекцию - ожидаемый результат: объект в коллекции, размер коллекции 0->1
    @Test
    public void userCanAddEntityToCollection() {
        User user1 = new User(20, "Вася", true);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);

        // проверяем, что размер копии списка всех элементов = 1
        assertEquals(1, entityManager1.getAllEntities().size());

        // проверяем, что первый объект из копии списка всех элементов equal user1
        assertEquals(user1, entityManager1.getAllEntities().getFirst());

        // проверяем, что поля объектов равны
        assertEquals(user1.getName(), entityManager1.getAllEntities().getFirst().getName());
        assertEquals(user1.getAge(), entityManager1.getAllEntities().getFirst().getAge());
        assertEquals(user1.isActive(), entityManager1.getAllEntities().getFirst().isActive());
    }

    // 2 - тест - переопределен ли equals
    @Test
    public void equalsShouldBeOverridden() {
        User user1 = new User(20, "Вася", true);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);

        User expectedUser = new User(20, "Вася", true);

        assertEquals(expectedUser, entityManager1.getAllEntities().getFirst());
    }

    // 3 - тест - переопределен ли hashcode
    @Test
    public void hashCodeShouldBeOverridden() {
        User user1 = new User(20, "Вася", true);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);

        User expectedUser = new User(20, "Вася", true);

        assertEquals(expectedUser.hashCode(), entityManager1.getAllEntities().getFirst().hashCode());
    }

    // 4 - тест - добавление null - допустим, по требованиям, мы ожидаем, что должно выбрасываться IllegalArgumentException
    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddingNullEntity() {
        EntityManager<User> entityManager1 = new EntityManager<>();
        assertThrows(IllegalArgumentException.class, () -> entityManager1.addEntity(null));
    }

    // 5 - тест - удаление элемента из коллекции -> Возвращает true, если объект был удалён + проверяем, что объект отсутствует в
    // списке

    @Test
    public void userCanRemoveEntity() {
        User user1 = new User(20, "Вася", true);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);

        assertTrue(entityManager1.removeEntity(user1));
        assertEquals(0, entityManager1.getAllEntities().size());
    }

    // 6 - тест - удаление элемента из коллекции -> Возвращает false, если объект не найден в коллекции
    @Test
    public void removeMethodReturnsFalseIfElementNotFoundInCollection() {
        User user1 = new User(20, "Вася", true);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);
        entityManager1.removeEntity(user1);

        assertFalse(entityManager1.removeEntity(user1));
    }

    // 7 - happy path: метод возвращает ожидаемый список пользователей в заданном возрастном диапазоне
    @Test
    public void userCanFilterByAge() {
        User user1 = new User(20, "Вася", true);
        User user2 = new User(10, "Петя", true);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);
        entityManager1.addEntity(user2);

        // проверка, что ожидаемое количество юзеров после фильтрации соответствует ожидаемому результату
        assertEquals(1, entityManager1.getEntitiesFilteredByAge(5, 10).size());

        // проверка, что отфильтрованный юзер равен ожидаемому юзеру
        assertEquals(user2, entityManager1.getEntitiesFilteredByAge(5, 10).getFirst());
    }


    // 8 - happy path: метод возвращает ожидаемый список пользователей, чьи имена соответствуют заданной строке.

    @Test
    public void userCanFilterByName() {
        User user1 = new User(20, "Вася", true);
        User user2 = new User(10, "Петя", true);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);
        entityManager1.addEntity(user2);

        // проверка, что ожидаемое количество юзеров после фильтрации соответствует ожидаемому результату
        assertEquals(1, entityManager1.getEntitiesFilteredByName("Вася").size());

        // проверка, что отфильтрованный юзер равен ожидаемому юзеру
        assertEquals(user1, entityManager1.getEntitiesFilteredByName("Вася").getFirst());
    }


    // 9 - happy path: метод возвращает ожидаемый список пользователей с заданным статусом активности

    @Test
    public void userCanFilterByActiveStatus() {
        User user1 = new User(20, "Вася", true);
        User user2 = new User(10, "Петя", false);
        EntityManager<User> entityManager1 = new EntityManager<>();
        entityManager1.addEntity(user1);
        entityManager1.addEntity(user2);

        // проверка, что ожидаемое количество юзеров после фильтрации соответствует ожидаемому результату
        assertEquals(1, entityManager1.getEntitiesFilteredByActiveStatus(true).size());

        // проверка, что отфильтрованный юзер равен ожидаемому юзеру
        assertEquals(user1, entityManager1.getEntitiesFilteredByActiveStatus(true).getFirst());
    }













}

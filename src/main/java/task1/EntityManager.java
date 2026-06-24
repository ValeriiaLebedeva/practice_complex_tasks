package task1;


/*
Описание:
Разработайте класс EntityManager<T>, который будет управлять коллекцией объектов произвольного типа T,
обеспечивая потокобезопасное добавление, удаление и получение элементов.
Класс также должен предоставлять специфические методы фильтрации данных,
которые позволяют пользователю извлекать элементы по определённым критериям.

Функциональные требования:
Добавление элементов: Метод для добавления объекта в коллекцию. Должен быть потокобезопасным.

Удаление элементов: Метод для удаления объекта из коллекции. Возвращает true, если объект был удалён, и false,
если объект не найден в коллекции. Должен быть потокобезопасным.

Получение всех элементов: Метод возвращает копию списка всех элементов, обеспечивая невозможность изменения исходной
коллекции через возвращаемый список.

Специализированные методы фильтрации:
Фильтрация по возрасту: Возвращает список пользователей в заданном возрастном диапазоне.
Фильтрация по имени: Возвращает список пользователей, чьи имена соответствуют заданной строке.
Фильтрация по активности: Возвращает список пользователей с заданным статусом активности.

 */

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class EntityManager <T extends Entity> {

    // коллекция элементов
    private final List<T> entities = new CopyOnWriteArrayList<>();

    // Добавление элементов: Метод для добавления объекта в коллекцию. Должен быть потокобезопасным.
    public void addEntity(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Ошибка, вы пытаетесь добавить null!");
        }
        entities.add(entity);
    }

    // Удаление элементов: Метод для удаления объекта из коллекции. Возвращает true, если объект был удалён, и false,
    // если объект не найден в коллекции. Должен быть потокобезопасным.
    public boolean removeEntity(T entity) {
        return entities.remove(entity);
    }


    // Получение всех элементов: Метод возвращает копию списка всех элементов, обеспечивая невозможность изменения исходной
    // коллекции через возвращаемый список.
    public List<T> getAllEntities() {return List.copyOf(entities);}

    // Фильтрация по возрасту: Возвращает список пользователей в заданном возрастном диапазоне.
    public List<T> getEntitiesFilteredByAge(int min, int max) {
        return entities.stream()
                .filter(entity -> (entity.getAge() >= min && entity.getAge() <= max))
                .toList();
    }

    // Фильтрация по имени: Возвращает список пользователей, чьи имена соответствуют заданной строке.
    public List<T> getEntitiesFilteredByName(String name) {
        return entities.stream()
                .filter(entity -> entity.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Фильтрация по активности: Возвращает список пользователей с заданным статусом активности.
    public List<T> getEntitiesFilteredByActiveStatus(boolean isActive) {
        return entities.stream()
                .filter(entity -> entity.isActive() == isActive)
                .toList();
    }

}

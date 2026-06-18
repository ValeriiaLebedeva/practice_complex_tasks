package task6;


//TaskService<T>: Сервис для управления задачами, включающий методы для добавления, удаления и поиска задач.
//Управление задачами:
//Добавление задачи: Метод для добавления новой задачи в список.
//Удаление задачи: Метод для удаления задачи по ID. Метод должен быть синхронизирован для предотвращения конкурентного доступа.
//Поиск задач: Методы для фильтрации задач по статусу и приоритету, а также сортировка задач по дате.
//Обработка данных:
//Использование Stream API для фильтрации и сортировки задач.
//Лямбда-выражения для сортировки задач по дате.

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService<T> {

    private final List<Task<T>> listOfTasks = new ArrayList<>();

    //Добавление задачи: Метод для добавления новой задачи в список.

    public synchronized void addTaskToList(Task<T> task) {

        boolean exists = listOfTasks.stream().
                anyMatch(t -> t.getId().equals(task.getId()));

        if (exists) {
            throw new InvalidTaskException("Задача с таким ID уже существует!");
        }

        listOfTasks.add(task);

    }

    //Удаление задачи: Метод для удаления задачи по ID. Метод должен быть синхронизирован для
    // предотвращения конкурентного доступа.

    public synchronized void removeTaskFromListById(T id) {

        boolean exists = listOfTasks.stream()
                .anyMatch(t -> t.getId().equals(id));

        if (!exists) {
            throw new InvalidTaskException("Задачи с таким ID в списке нет!");
        }

        listOfTasks.removeIf(t -> t.getId().equals(id));
    }

    //Поиск задач: Методы для фильтрации задач по статусу и приоритету, а также сортировка задач по дате.

    public synchronized List<Task<T>> getListOfTasksFilteredByStatus(Status status) {
        return listOfTasks.stream()
                .filter(t -> t.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public synchronized List<Task<T>> getListOfTasksFilteredByPriority(Priority priority) {
        return listOfTasks.stream()
                .filter(t -> t.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    public synchronized List<Task<T>> getListOfTasksSortedByDate() {
        return listOfTasks.stream()
                .sorted(Comparator.comparing(Task::getDate))
                .collect(Collectors.toList());
    }


    //Метод по получению копии списка текущих задач

    public synchronized List<Task<T>> getListOfTasks() {
        return List.copyOf(listOfTasks);
    }


}

/*
тесты:

1 - добавление задачи (задача с таким id не была добавлена ранее)
2 - добавление задачи (задача с таким id была добавлена ранее)
3 - удаление задачи (задача с таким id была добавлена ранее)
4 - удаление задачи (задача с таким id не была добавлена ранее)
5 - фильтрация задач по статусу (задачи с данным статусом есть в списке)
6 - фильтрация задач по статусу (задач с данным статусом нет в списке)
7 - фильтрация задач по приоритету (задачи с данным приоритетом есть в списке)
8 - фильтрация задач по приоритету (задач с данным приоритетом нет в списке)
9 - сортировка задач по дате


 */

import org.junit.jupiter.api.Test;
import task6.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    // 1 - добавление задачи (задача с таким id не была добавлена ранее)
    @Test
    public void testAddTask() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        TaskService<Integer> taskService1 = new TaskService<>();
        taskService1.addTaskToList(task1);

        assertEquals(1, taskService1.getListOfTasks().size());
        assertEquals(task1, taskService1.getListOfTasks().getFirst());
    }

    // 2 - добавление задачи (задача с таким id была добавлена ранее)

    @Test
    public void testAddTaskWithSameId() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        TaskService<Integer> taskService1 = new TaskService<>();
        taskService1.addTaskToList(task1);

        assertThrows(InvalidTaskException.class, () -> taskService1.addTaskToList(task1));
    }


    // 3 - удаление задачи (задача с таким id была добавлена ранее)

    @Test
    public void testRemoveTask() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        TaskService<Integer> taskService1 = new TaskService<>();
        taskService1.addTaskToList(task1);
        taskService1.removeTaskFromListById(task1.getId());

        assertFalse(taskService1.getListOfTasks().contains(task1));
    }

    // 4 - удаление задачи (задача с таким id не была добавлена ранее)

    @Test
    public void testRemoveTaskIdNotExist() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        TaskService<Integer> taskService1 = new TaskService<>();
        taskService1.addTaskToList(task1);

        assertThrows(InvalidTaskException.class, () -> taskService1.removeTaskFromListById(2));
    }


    // 5 - фильтрация задач по статусу (задачи с данным статусом есть в списке)

    @Test
    public void testFilterByStatus() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.LOW, LocalDate.now());

        TaskService<Integer> taskService1 = new TaskService<>();

        taskService1.addTaskToList(task1);
        taskService1.addTaskToList(task2);

        assertEquals(1, taskService1.getListOfTasksFilteredByStatus(Status.NEW).size());
        assertEquals(task1, taskService1.getListOfTasksFilteredByStatus(Status.NEW).getFirst());
    }


    // 6 - фильтрация задач по статусу (задач с данным статусом нет в списке)

    @Test
    public void testFilterByStatusNotInTheList() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.LOW, LocalDate.now());

        TaskService<Integer> taskService1 = new TaskService<>();

        taskService1.addTaskToList(task1);
        taskService1.addTaskToList(task2);

        assertEquals(0, taskService1.getListOfTasksFilteredByStatus(Status.FINISHED).size());
    }


    // 7 - фильтрация задач по приоритету (задачи с данным приоритетом есть в списке)

    @Test
    public void testFilterByPriority() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.now());

        TaskService<Integer> taskService1 = new TaskService<>();

        taskService1.addTaskToList(task1);
        taskService1.addTaskToList(task2);

        assertEquals(1, taskService1.getListOfTasksFilteredByPriority(Priority.LOW).size());
        assertEquals(task1, taskService1.getListOfTasksFilteredByPriority(Priority.LOW).getFirst());
    }

    // 8 - фильтрация задач по приоритету (задач с данным приоритетом нет в списке)


    @Test
    public void testFilterByPriorityNotInTheList() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.now());
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.now());

        TaskService<Integer> taskService1 = new TaskService<>();

        taskService1.addTaskToList(task1);
        taskService1.addTaskToList(task2);

        assertEquals(0, taskService1.getListOfTasksFilteredByPriority(Priority.HIGH).size());
    }


    // 9 - сортировка задач по дате
    @Test
    public void testSortByDate() {
        Task<Integer> task1 = new Task<>(1, Status.NEW, Priority.LOW, LocalDate.of(2026, 5, 1));
        Task<Integer> task2 = new Task<>(2, Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.of(2026, 5, 4));
        Task<Integer> task3 = new Task<>(3, Status.NEW, Priority.HIGH, LocalDate.of(2026, 5, 2));

        TaskService<Integer> taskService1 = new TaskService<>();

        taskService1.addTaskToList(task1);
        taskService1.addTaskToList(task2);
        taskService1.addTaskToList(task3);

        assertEquals(task1, taskService1.getListOfTasksSortedByDate().getFirst());
        assertEquals(task2, taskService1.getListOfTasksSortedByDate().getLast());

    }

}

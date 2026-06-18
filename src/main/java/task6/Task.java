package task6;


import java.time.LocalDate;

// Task<T>: Класс, представляющий задачу. Должен содержать поля для ID, статуса, приоритета и даты.
public class Task<T> {

    private T id;
    private Status status;
    private Priority priority;
    private LocalDate date;

    public Task(T id, Status status, Priority priority, LocalDate date) {
        this.id = id;
        this.status = status;
        this.priority = priority;
        this.date = date;
    }

    public T getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", status=" + status +
                ", priority=" + priority +
                ", date=" + date +
                '}';
    }
}


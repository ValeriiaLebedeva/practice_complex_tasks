package task3;


/*
Класс StudentGrade<T>:
Поля для имени студента, предмета и оценки.
Оценка должна быть типа T, который расширяет класс Number.
Конструктор для инициализации всех полей.
Геттеры для доступа к полям.

 */
public class StudentGrade <T extends Number> {

    private String name;
    private String subject;
    private T grade;

    public StudentGrade(String name, String subject, T grade) {
        this.name = name;
        this.subject = subject;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public T getGrade() {
        return grade;
    }
}

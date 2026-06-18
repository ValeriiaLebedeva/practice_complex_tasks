package task3;

/*

Сервис GradeService<T>:
Список List<StudentGrade<T>> для хранения оценок.
Метод для добавления оценки (addGrade), который также валидирует оценку на предмет того, что она не отрицательна.
Метод для расчёта среднего значения оценок по конкретному предмету.
Обработка исключений через InvalidGradeException, если оценка некорректна.
Многопоточность:
Обеспечение потокобезопасности при добавлении оценок с использованием synchronized.


 */

import java.util.ArrayList;
import java.util.List;

public class GradeService <T extends Number> {

    // Список List<StudentGrade<T>> для хранения оценок.
    private final List<StudentGrade<T>> gradeList = new ArrayList<>();

    // Метод для добавления оценки (addGrade), который также валидирует оценку на предмет того, что она не отрицательна.
    public synchronized void addGrade(StudentGrade<T> studentGrade) {
        if (studentGrade.getGrade().doubleValue() < 0) {
            throw new InvalidGradeException("Оценка не может быть отрицательной!");
        }
        gradeList.add(studentGrade);
    }

    // Метод для расчёта среднего значения оценок по конкретному предмету.
    public synchronized double getAverageGradeValueBySubject(String subject) {
        return gradeList.stream()
                .filter(studentGrade -> studentGrade.getSubject().equalsIgnoreCase(subject))
                .mapToDouble(studentGrade -> studentGrade.getGrade().doubleValue())
                .average()
                .orElse(0.0);
    }

    // Метод для получения копии списка оценок
    public synchronized List<StudentGrade<T>> getGradeList() {
        return List.copyOf(gradeList);
    }
}

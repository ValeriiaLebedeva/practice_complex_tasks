/*
тесты:

1 - пользователь может добавить валидную оценку
2 - пользователь не может добавить отрицательную оценку
3 - пользователь может рассчитать среднюю оценку по конкретному предмету
4 - пользователь может рассчитать среднюю оценку по конкретному предмету игнорируя lower/uppercase
5 - пользователь получает 0.0 если оценок по категории нет в списке

 */

import org.junit.jupiter.api.Test;
import task3.GradeService;
import task3.InvalidGradeException;
import task3.StudentGrade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GradeServiceTest {

    // 1 - пользователь может добавить валидную оценку
    @Test
    public void testAddValidGrade() {
        StudentGrade<Double> studentGrade1 = new StudentGrade<>("Вася", "Математика", 80.0);
        GradeService<Double> gradeService1 = new GradeService<>();
        gradeService1.addGrade(studentGrade1);

        assertEquals(studentGrade1, gradeService1.getGradeList().getFirst());
        assertEquals(1, gradeService1.getGradeList().size());
        assertEquals(studentGrade1.getGrade(), gradeService1.getGradeList().getFirst().getGrade());
    }


    // 2 - пользователь не может добавить отрицательную оценку
    @Test
    public void testNotAddInvalidGrade() {
        StudentGrade<Double> studentGrade1 = new StudentGrade<>("Вася", "Математика", -80.0);
        GradeService<Double> gradeService1 = new GradeService<>();
        assertThrows(InvalidGradeException.class, () -> gradeService1.addGrade(studentGrade1));
    }

    // 3 - пользователь может рассчитать среднюю оценку по конкретному предмету
    @Test
    public void testCalcAvgValueBySubject() {
        StudentGrade<Double> studentGrade1 = new StudentGrade<>("Вася", "Математика", 80.0);
        StudentGrade<Double> studentGrade2 = new StudentGrade<>("Вася", "Математика", 90.0);

        GradeService<Double> gradeService1 = new GradeService<>();
        gradeService1.addGrade(studentGrade1);
        gradeService1.addGrade(studentGrade2);

        assertEquals(85.0, gradeService1.getAverageGradeValueBySubject("Математика"));
    }

    // 4 - пользователь может рассчитать среднюю оценку по конкретному предмету игнорируя lower/uppercase

    @Test
    public void testCalcAvgValueBySubjectIgnoreSubjectCase() {
        StudentGrade<Double> studentGrade1 = new StudentGrade<>("Вася", "Математика", 80.0);
        StudentGrade<Double> studentGrade2 = new StudentGrade<>("Вася", "математика", 90.0);

        GradeService<Double> gradeService1 = new GradeService<>();
        gradeService1.addGrade(studentGrade1);
        gradeService1.addGrade(studentGrade2);

        assertEquals(85.0, gradeService1.getAverageGradeValueBySubject("математика"));
    }

    // 5 - пользователь получает 0.0 если оценок по категории нет в списке
    @Test
    public void testForSubjectNotExistInGradeList() {
        StudentGrade<Double> studentGrade1 = new StudentGrade<>("Вася", "Математика", 80.0);
        StudentGrade<Double> studentGrade2 = new StudentGrade<>("Вася", "математика", 90.0);

        GradeService<Double> gradeService1 = new GradeService<>();
        gradeService1.addGrade(studentGrade1);
        gradeService1.addGrade(studentGrade2);

        assertEquals(0.0, gradeService1.getAverageGradeValueBySubject("английский"));
    }


}

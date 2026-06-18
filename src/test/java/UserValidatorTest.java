import org.junit.jupiter.api.Test;
import task2.InvalidUserException;
import task2.User;
import task2.UserValidator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*

тесты:

1 - проверка, что валидация возможна, если validationEnabled = true
2 - проверка, что валидация невозможна, если validationEnabled = false
3 - проверка имени: Имя должно быть не пустым и начинаться с заглавной буквы - проверяется в тесте 1
4 - проверка имени - пустое имя
5 - проверка имени - начинается с маленькой буквы
6 - проверка имени - null
7 - проверка возраста: Возраст должен быть в пределах от 18 до 100 лет - проверяется в тесте 1
8 - проверка возраста: возраста меньше 18
9 - проверка возраста: возраст больше 100
10 - проверка email - корректный email - проверяется в тесте 1
11 - проверка email - некоррекный email
12 - проверка email - пустой email
13 - проверка email - null

 */


public class UserValidatorTest {

    // 1 - проверка, что валидация возможна, если validationEnabled = true
    @Test
    public void userCanValidateIfValidationEnabledIsTrue() {

        User user1 = new User("Вася", 19, "vasya@gmail.com");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertTrue(userValidator.validateName(user1));
        assertTrue(userValidator.validateAge(user1));
        assertTrue(userValidator.validateEmail(user1));
    }

    // 2 - проверка, что валидация невозможна, если validationEnabled = false
    @Test
    public void userCanNotValidateIfValidationEnabledIsFalse() {
        User user1 = new User("Вася", 19, "vasya@gmail.com");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(false);

        assertThrows(InvalidUserException.class, () -> userValidator.validateName(user1));
        assertThrows(InvalidUserException.class, () -> userValidator.validateAge(user1));
        assertThrows(InvalidUserException.class, () -> userValidator.validateEmail(user1));
    }


    // 4 - проверка имени - пустое имя
    @Test
    public void testEmptyName() {
        User user1 = new User("", 19, "vasya@gmail.com");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateName(user1));
    }

    // 5 - проверка имени - начинается с маленькой буквы
    @Test
    public void testLowerCaseName() {
        User user1 = new User("вася", 19, "vasya@gmail.com");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateName(user1));
    }

    // 6 - проверка имени - null
    @Test
    public void testNullName() {
        User user1 = new User(null, 19, "vasya@gmail.com");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateName(user1));
    }

    // 8 - проверка возраста: возраста меньше 18
    @Test
    public void testAgeUnder18() {
        User user1 = new User("Вася", 17, "vasya@gmail.com");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateAge(user1));
    }

    // 9 - проверка возраста: возраст больше 100
    @Test
    public void testAgeMore100() {
        User user1 = new User("Вася", 101, "vasya@gmail.com");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateAge(user1));
    }

    // 11 - проверка email - некоррекный email
    @Test
    public void testIncorrectEmail() {
        User user1 = new User("Вася", 19, "vasya-gmail");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateEmail(user1));
    }

    // 12 - проверка email - пустой email
    @Test
    public void testEmptyEmail() {
        User user1 = new User("Вася", 19, "");
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateEmail(user1));
    }

    // 13 - проверка email - null
    @Test
    public void testNullEmail() {
        User user1 = new User("Вася", 19,  null);
        UserValidator userValidator = new UserValidator();
        userValidator.setValidation(true);

        assertThrows(InvalidUserException.class, () -> userValidator.validateEmail(user1));
    }


}

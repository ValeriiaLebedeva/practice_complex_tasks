package task2;


 /*

Описание:
Разработайте систему валидации для модели данных пользователя, которая проверяет корректность имени, возраста и электронной почты.
Валидация должна управляться через глобальный флаг validationEnabled, который может быть включен или выключен.
Если данные не проходят валидацию, должно выбрасываться специализированное исключение InvalidUserException.

Модель данных:
User: Класс пользователя с атрибутами для имени, возраста и электронной почты.
Класс валидатора:
UserValidator: Сервис, который предоставляет методы для проверки объектов User на соответствие определенным правилам.
Функциональные требования:
Проверка имени: Имя должно быть не пустым и начинаться с заглавной буквы.
Проверка возраста: Возраст должен быть в пределах от 18 до 100 лет.
Проверка email: Email должен соответствовать стандартному формату электронной почты.
Управление валидацией: Валидация данных должна происходить только если флаг validationEnabled установлен в true.
Исключения: При обнаружении невалидных данных необходимо выбрасывать InvalidUserException.
  */

public class UserValidator {

    private boolean validationEnabled;

    // установка флага validationEnabled
    public void setValidation(boolean validationEnabled) {
        this.validationEnabled = validationEnabled;
    }

    public void validate(User user) {
        if (!validationEnabled) return;
        validateName(user.getName());
        validateAge(user.getAge());
        validateEmail(user.getEmail());
    }

    // Проверка имени: Имя должно быть не пустым и начинаться с заглавной буквы.
    private void validateName(String name) {
        if (name == null ) {throw new InvalidUserException("Name is null");}
        if (name.isEmpty() ) {throw new InvalidUserException("Name is empty!");}
        if (!(Character.isUpperCase(name.charAt(0)))) {throw new InvalidUserException("Name starts with a lowercase letter!");}
    }


    // Проверка возраста: Возраст должен быть в пределах от 18 до 100 лет.

    private void validateAge(int age) {
        if (age < 18 || age > 100) {throw new InvalidUserException("Age out of range");}
    }


    // Проверка email: Email должен соответствовать стандартному формату электронной почты

    private void validateEmail(String email) {
        if (email == null ) {throw new InvalidUserException("Email is null");}
        if (email.isEmpty() ) {throw new InvalidUserException("Email is empty!");}
        if (!(email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$"))) {
            throw new InvalidUserException("Email format is not correct!");}
    }


}

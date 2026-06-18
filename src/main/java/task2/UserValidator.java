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

    // Проверка имени: Имя должно быть не пустым и начинаться с заглавной буквы.
    public boolean validateName(User user) {
        if (!validationEnabled) {throw new InvalidUserException("Validation is unabled!");}
        if (user.getName() == null ) {throw new InvalidUserException("Name is null");}
        if (user.getName().isEmpty() ) {throw new InvalidUserException("Name is empty!");}
        if (!(Character.isUpperCase(user.getName().charAt(0)))) {throw new InvalidUserException("Name starts with a lowercase letter!");}
        else return true;
    }


    // Проверка возраста: Возраст должен быть в пределах от 18 до 100 лет.

    public boolean validateAge(User user) {
        if (!validationEnabled) {throw new InvalidUserException("Validation is unabled!");}
        if (user.getAge() < 18 || user.getAge() > 100) {throw new InvalidUserException("Age out of range");}
        else return true;
    }


    // Проверка email: Email должен соответствовать стандартному формату электронной почты

    public boolean validateEmail(User user) {
        if (!validationEnabled) {throw new InvalidUserException("Validation is unabled!");}
        if (user.getEmail() == null ) {throw new InvalidUserException("Email is null");}
        if (user.getEmail().isEmpty() ) {throw new InvalidUserException("Email is empty!");}
        if (!(user.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$"))) {
            throw new InvalidUserException("Email format is not correct!");}
        else return true;
    }


}

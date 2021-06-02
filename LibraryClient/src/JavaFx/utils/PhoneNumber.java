package JavaFx.utils;

/**
 * Класс-утилита для работы с телефонными номерами
 */
public class PhoneNumber {
    /**
     * Проверяет телефонный номер на правильность соответствием шаблону
     * @param phoneNumber String
     * @return boolean
     */
    public static boolean isValidatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");

        // российские мобильные и городские имена с кодом из 3 цифр
    }
}

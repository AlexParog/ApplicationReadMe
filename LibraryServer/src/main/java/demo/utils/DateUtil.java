package demo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс-утилита для работы с датами
 */
public class DateUtil {
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String DATE_PATTERN_LOCAL = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER_LOCAL = DateTimeFormatter
            .ofPattern(DATE_PATTERN_LOCAL);

    /**
     * Форматирует дату по указанному формату
     * @param date LocalDate
     * @return String
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_TIME_FORMATTER.format(date);
    }

    /**
     * Парсит дату из строки
     * @param dateString String
     * @return LocalDate
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_TIME_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            try{
                return DATE_TIME_FORMATTER_LOCAL.parse(dateString, LocalDate::from);
            }catch (DateTimeParseException k){
                return null;
            }
        }
    }

    /**
     * Проверяет правильность даты
     * @param dateString String
     * @return boolean
     */
    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
}

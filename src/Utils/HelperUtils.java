package Utils;

import entity.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HelperUtils {

    public static <T extends Person> boolean checkIfIdExists(List<T> list, String id) {
        if (list == null || list.isEmpty()) {
            return false; // no items yet
        }
        for (T item : list) {
            if (item.getId().equalsIgnoreCase(id)) {
                return true; // ID exists
            }
        }
        return false; // ID not found
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNull(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isNotNull(String str) {
        return str != null && !str.isEmpty();
    }

    //String Validation Methods (Overloaded)
    public static boolean isValidString(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isValidString(String str, int minLength) { //checks minimum length
        return isValidString(str) && str.length() >= minLength;
    }

    public static boolean isValidString(String str, int minLength, int maxLength) { //checks length range
        return isValidString(str) && str.length() >= minLength && str.length() <= maxLength;
    }

    public static boolean isValidString(String str, String regex) { //validates against regex pattern
        return isValidString(str) && str.matches(regex);
    }

    public static String generateId() {
        return UUID.randomUUID().toString(); // Example: "3f1f7e2b-1b8a-4d2f-bf87-5f0a4e3c8f1d"
    }

    // Generate ID with prefix (e.g., PAT-12345)
    public static String generateId(String prefix) {
        return prefix + "-" + getRandomNumber(5);
    }

    // Generate ID with prefix and custom numeric length
    public static String generateId(String prefix, int length) {
        return prefix + "-" + getRandomNumber(length > 0 ? length : 5);
    }

    // Generate ID with prefix and suffix
    public static String generateId(String prefix, String suffix) {
        return prefix + "-" + getRandomNumber(5) + "-" + suffix;
    }

    public static String getRandomNumber(int length) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = (int) (Math.random() * 10);
            number.append(digit);
        }
        return number.toString();
    }

    //Date Validation Methods (Overloaded)
    public static String isValidDate(Date date) { //checks not null and valid
        if (date == null) {
            return "Date is null";
        }
        Date today = new Date();
        if (date.before(today)) {
            return "Date cannot be in the past";
        }
        return null; //valid
    }

    public static String isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return "Date string is null or empty";
        }

        dateStr = dateStr.trim();

        // Check format yyyy-MM-dd
        if (!dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return "Invalid date format (expected YYYY-MM-DD)";
        }

        String[] parts = dateStr.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        // Basic checks
        if (month < 1 || month > 12) return "Invalid month";
        if (day < 1) return "Invalid day";

        // Days per month
        int[] daysInMonth = {31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (day > daysInMonth[month - 1]) return "Invalid day for the given month";

        return null; // valid
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }



    public static String isValidDate(Date date, Date minDate, Date maxDate) { //range check
        if (date == null) {
            return "Date is null";
        }
        if (minDate != null && date.before(minDate)) {
            return "Date is before minimum allowed date";
        }
        if (maxDate != null && date.after(maxDate)) {
            return "Date is after maximum allowed date";
        }
        return null;
    }

    public static String isFutureDate(Date date) {
        if (date == null) {
            return "Date is null";
        }
        Date today = new Date();
        if (date.after(today)) {
            return null; // valid
        } else {
            return "Date is not in the future";
        }
    }

    public static String isPastDate(Date date) {
        if (date == null) {
            return "Date is null";
        }
        Date today = new Date();
        if (date.before(today)) {
            return null; // valid
        } else {
            return "Date is not in the past";
        }
    }

    public static String isToday(Date date) {
        if (date == null) {
            return "Date is null";
        }
        return date.equals(LocalDate.now()) ? null : "Date is not today";
    }

    //Numeric Validation Methods
    public static String isValidNumber(int num, int min, int max) { //range check
        if (num > max) {
            return "Number is above maximum value";
        }
        if (num < min) {
            return "Number is below minimum value";
        }
        return null; // valid
    }

    public static String isValidNumber(double num, double min, double max) {
        if (num < min) {
            return "Number " + num + " is below minimum value " + min;
        }
        if (num > max) {
            return "Number " + num + " is above maximum value " + max;
        }
        return null; // valid
    }

    public static boolean isPositive(int num) {
        return num > 0;
    }

    public static boolean isPositive(double num) {
        return num > 0;
    }

    public static boolean isNegative(int num) {
        return num < 0;
    }

    public static boolean isNegative(double num) {
        return num < 0;
    }

    //Input Validation Methods
    public static boolean isValidAge(int age) {
        return age >= 0 && age <= 200;
    }

    public static boolean isValidAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return false;
        }
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return isValidAge(age);
    }

    // Integer validation
    public static String isValidInteger(String str) {
        if (str != null && str.matches("-?\\d+")) {
            System.out.println("Valid integer: " + str);
            return null; // valid
        }
        return "Invalid integer: " + str;
    }

    // Double/Decimal validation
    public static String isValidDouble(String str) {
        if(str != null && str.matches("-?\\d+(\\.\\d+)?")) {
            System.out.println("Valid double: " + str);
            return null; // valid
        }
        return "Invalid double: " + str;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}

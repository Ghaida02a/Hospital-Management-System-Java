package Utils;

import entity.Person;

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
    public static boolean isNull(Object obj){
        return obj == null;
    }

    public static boolean isNull(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNull(Object obj){
        return obj != null;
    }

    public static boolean isNotNull(String str) {
        return str != null && !str.isEmpty();
    }

    //String Validation Methods (Overloaded)
    public static boolean isValidString(String str){
        return str != null && !str.isEmpty();
    }

    public static boolean isValidString(String str, int minLength){ //checks minimum length
        return isValidString(str) && str.length() >= minLength;
    }

    public static boolean isValidString(String str, int minLength, int maxLength){ //checks length range
        return isValidString(str) && str.length() >= minLength && str.length() <= maxLength;
    }

    public static boolean isValidString(String str, String regex){ //validates against regex pattern
        return isValidString(str) && str.matches(regex);
    }

    /*
     * ID Generation Methods (Overloaded)
     * • generateId() - generates random UUID
     * • generateId(String prefix) - generates ID with prefix (e.g., "PAT-12345")
     * • generateId(String prefix, int length) - custom length
     * • generateId(String prefix, String suffix) - with both prefix and suffix
     */

    public static String generateId() {
        return UUID.randomUUID().toString(); // Example: "3f1f7e2b-1b8a-4d2f-bf87-5f0a4e3c8f1d"
    }

//    // 2. Generate ID with prefix (e.g., PAT-12345)
//    public static String generateId(String prefix) {
//        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 99999); // 5-digit number
//        return prefix + "-" + randomNumber;
//    }
//
//    // 3. Generate ID with prefix and custom length (numeric part)
//    public static String generateId(String prefix, int length) {
//        if (length <= 0) length = 5; // default length
//        StringBuilder number = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            number.append(ThreadLocalRandom.current().nextInt(0, 10)); // random digit
//        }
//        return prefix + "-" + number.toString();
//    }
//
//    // 4. Generate ID with prefix and suffix
//    public static String generateId(String prefix, String suffix) {
//        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 99999);
//        return prefix + "-" + randomNumber + "-" + suffix;
//    }
    /*
     * Date Validation Methods (Overloaded)
     * • isValidDate(Date date) - checks not null and valid
     * • isValidDate(String dateStr) - parses and validates string date
     * • isValidDate(Date date, Date minDate, Date maxDate) - range check
     * • isFutureDate(Date date)
     * • isPastDate(Date date)
     * • isToday(Date date)
     */
    /*
     * Numeric Validation Methods (Overloaded)
     * • isValidNumber(int num, int min, int max) - range check
     * • isValidNumber(double num, double min, double max)
     * • isPositive(int num)
     * • isPositive(double num)
     * • isNegative(int num)
     * • isNegative(double num)
     */
    /*
     * Input Validation Methods (Overloaded)
     * • isValidAge(int age)
     * • isValidAge(LocalDate dateOfBirth)
     */
}

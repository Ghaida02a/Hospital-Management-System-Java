package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputHandler {

    public static String getStringInput(String prompt){

        return prompt;
    }
    public static void getIntInput(String prompt){

    }
    public static Integer getIntInput(String prompt, int min, int max) {
        return null;
    }
    public static void getDoubleInput(String prompt){

    }
    public static LocalDate getDateInput(String prompt){
        LocalDate date = null;
        while (date == null) {
            String input = getStringInput(prompt + " (YYYY-MM-DD): ");
            try {
                date = LocalDate.parse(input);

                // Validation to ensure DOB is not in the future
                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Date cannot be in the future. Please enter a past or current date.");
                    date = null; // reset date to continue loop
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format or date value. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }
    public static void getConfirmation(String prompt) {//yes/no

    }
}

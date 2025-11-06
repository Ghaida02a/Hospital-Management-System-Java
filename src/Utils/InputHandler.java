package Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    //String Input Method
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (HelperUtils.isValidString(input)) { // returns true if not null/empty
                return input;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
    }

    //Integer Input Method
    public static Integer getIntInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            String validation = HelperUtils.isValidInteger(input);
            if (validation == null) {
                return Integer.parseInt(input);
            }
            System.out.println(validation);
        }
    }

    public static Integer getIntInput(String prompt, int min, int max) {
        while (true) {
            Integer input = getIntInput(prompt);
            String validation = HelperUtils.isValidNumber(input, min, max);
            if (validation == null) {
                return input;
            }
            System.out.println(validation);
        }
    }

    public static Double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            String validation = HelperUtils.isValidDouble(input);
            if (validation == null) {
                return Double.parseDouble(input);
            }
            System.out.println(validation);
        }
    }


    public static LocalDate getDateInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt + " (YYYY-MM-DD): ");
            String validation = HelperUtils.isValidDate(input);
            if (validation == null) {
                return LocalDate.parse(input);
            } else {
                System.out.println(validation);
            }
        }
    }

    public static LocalTime getTimeInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt + " (HH:mm): ");
            String validation = HelperUtils.isValidTime(input);
            if (validation == null) {
                return LocalTime.parse(input);
            } else {
                System.out.println(validation);
            }
        }
    }


    public static boolean getConfirmation(String prompt) {  //yes/no
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter yes or no.");
            }
        }
    }

    public static String getGenderInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt + " (Male/Female): ");
            input = input.trim();

            // Accept full words or just first letter (case-insensitive)
            if (input.equalsIgnoreCase("Male") || input.equalsIgnoreCase("M")) {
                return "Male";
            } else if (input.equalsIgnoreCase("Female") || input.equalsIgnoreCase("F")) {
                return "Female";
            } else {
                System.out.println("Invalid input. Please enter 'Male' or 'Female'.");
            }
        }
    }

    public static String getPhoneNumberInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt);

            if (!input.matches("\\d+")) {
                System.out.println("Invalid phone number. Only digits allowed.");
                continue;
            }

            if (input.length() != 8) { // must be exactly 8 digits
                System.out.println("Phone number must be exactly 8 digits.");
                continue;
            }

            return input;
        }
    }


    public static String getEmailInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt);
            if (HelperUtils.isValidEmail(input)) {
                return input;
            }
            System.out.println("Invalid email format. Try again.");
        }
    }

    public static boolean getBooleanInput(String s) {
        if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y")) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Integer> getIntegerList(String s) {
        List<Integer> integerList = new ArrayList<>();
        String[] parts = s.split(",");
        for (String part : parts) {
            try {
                integerList.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Warning: invalid integer '" + part + "' ignored.");
            }
        }
        return integerList;
    }
}

package service;

import Utils.HelperUtils;
import entity.Nurse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NurseService {
    public static List<Nurse> nurseList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static Nurse addNurse() {
        Nurse nurse = new Nurse();

        System.out.print("Enter Nurse ID: ");
        String idInput = scanner.nextLine();
        if (!HelperUtils.checkIfIdExists(nurseList, idInput)) {
            nurse.setId(idInput);
            nurse.setNurseId(idInput);
        } else {
            System.out.println("Warning: Nurse ID already exists. Leave blank or use a new ID.");
        }

        System.out.print("Enter First Name: ");
        nurse.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        nurse.setLastName(scanner.nextLine());

        System.out.print("Enter Date of Birth (YYYY-MM-DD) or leave blank: ");
        String dobInput = scanner.nextLine().trim();
        if (!dobInput.isEmpty()) {
            LocalDate dob = LocalDate.parse(dobInput);
            nurse.setDateOfBirth(dob);
        }

        System.out.print("Enter Gender: ");
        nurse.setGender(scanner.nextLine());

        System.out.print("Enter Phone Number: ");
        nurse.setPhoneNumber(scanner.nextLine());

        System.out.print("Enter Email: ");
        nurse.setEmail(scanner.nextLine());

        System.out.print("Enter Address: ");
        nurse.setAddress(scanner.nextLine());

        System.out.print("Enter Department ID: ");
        nurse.setDepartmentId(scanner.nextLine());

        System.out.print("Enter Shift (Morning/Evening/Night): ");
        nurse.setShift(scanner.nextLine());

        System.out.print("Enter Qualification: ");
        nurse.setQualification(scanner.nextLine());

        // initialize assigned patients list
        nurse.setAssignedPatients(new ArrayList<>());

        return nurse;
    }

    public static void save(Nurse nurse) {
        if (nurse != null) {
            nurseList.add(nurse);
            System.out.println("Nurse added successfully!\n");
        }
    }

    public static void editNurse(String nurseId, Nurse updatedNurse) {
        if (nurseList.isEmpty()) {
            System.out.println("No nurses available to edit.");
            return;
        }
        for (int i = 0; i < nurseList.size(); i++) {
            if (nurseList.get(i).getId().equals(nurseId)) {
                nurseList.set(i, updatedNurse);
                System.out.println("Nurse updated successfully!\n");
                return;
            }
        }
        System.out.println("Nurse with ID " + nurseId + " not found.\n");
    }

    public static void removeNurse(String nurseId) {
        if (nurseList.isEmpty()) {
            System.out.println("The list is empty. No nurse removed.");
            return;
        }

        boolean removed = nurseList.removeIf(n -> n.getId().equals(nurseId));

        if (removed) {
            System.out.println("Nurse with ID " + nurseId + " removed successfully!");
        } else {
            System.out.println("Nurse with ID " + nurseId + " not found.");
        }
    }

    public static Nurse getNurseById(String nurseId) {
        for (Nurse nurse : nurseList) {
            if (nurse.getId().equals(nurseId)) {
                return nurse;
            }
        }
        System.out.println("Nurse not found.\n");
        return null;
    }

    public static void displayAllNurses() {
        if (nurseList.isEmpty()) {
            System.out.println("No nurses found.\n");
            return;
        }

        System.out.println("===== Nurses List =====");
        for (Nurse nurse : nurseList) {
            nurse.displayInfo();
            System.out.println("------------------------");
        }
        System.out.println("========================\n");
    }

    public static List<Nurse> getNursesByDepartment(String departmentId) {
        List<Nurse> result = new ArrayList<>();
        for (Nurse nurse : nurseList) {
            if (nurse.getDepartmentId() != null && nurse.getDepartmentId().equalsIgnoreCase(departmentId)) {
                result.add(nurse);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No nurses found in department: " + departmentId);
        }
        return result;
    }

    public static List<Nurse> getNursesByShift(String shift) {
        List<Nurse> result = new ArrayList<>();
        if (shift == null) return result;
        for (Nurse nurse : nurseList) {
            if (nurse.getShift() != null && nurse.getShift().equalsIgnoreCase(shift)) {
                result.add(nurse);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No nurses found with shift: " + shift);
        }
        return result;
    }

    public static List<Nurse> searchNursesByName(String name) {
        List<Nurse> searchResults = new ArrayList<>();
        if (name == null) return searchResults;
        String searchName = name.toLowerCase();

        for (Nurse nurse : nurseList) {
            if (nurse.getFirstName() != null && nurse.getFirstName().toLowerCase().contains(searchName)) {
                searchResults.add(nurse);
            }
        }
        return searchResults;
    }
}

package service;

import Utils.HelperUtils;
import Utils.InputHandler;
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

        String generatedId;
        do {
            generatedId = HelperUtils.generateId("NUR");
        }
        while (HelperUtils.checkIfIdExists(nurseList, generatedId)); // ensure uniqueness
        nurse.setId(generatedId);
        System.out.println("Nurse ID: " + nurse.getId());

        nurse.setFirstName(InputHandler.getStringInput("Enter First Name: "));
        nurse.setLastName(InputHandler.getStringInput("Enter Last Name: "));

        nurse.setDateOfBirth(InputHandler.getDateInput("Enter Date of Birth"));

        nurse.setGender(InputHandler.getGenderInput("Enter Gender: "));

        nurse.setPhoneNumber(InputHandler.getPhoneNumberInput("Enter Phone Number: "));

        nurse.setEmail(InputHandler.getEmailInput("Enter Email: "));

        nurse.setAddress(InputHandler.getStringInput("Enter Address: "));

        nurse.setDepartmentId(InputHandler.getIntInput("Enter Department ID: ").toString());

        nurse.setShift(InputHandler.getStringInput("Enter Shift (Morning/Evening/Night): "));

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
        Nurse existingNurse = getNurseById(nurseId);
        if (existingNurse != null) {
            updatedNurse.setId(existingNurse.getId());

            int index = nurseList.indexOf(existingNurse);
            nurseList.set(index, updatedNurse);
            System.out.println("Nurse updated successfully!\n");
        } else {
            System.out.println("Nurse not found with ID: " + nurseId);
        }
    }

    public static void removeNurse(String nurseId) {
        Nurse nurse = getNurseById(nurseId); // use the method
        if (nurse != null) {
            nurseList.remove(nurse);
            System.out.println("Nurse with ID " + nurseId + " removed successfully!");
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
            nurse.displayInfo("");
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
}

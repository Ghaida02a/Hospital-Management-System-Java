package main;

import entity.Patient;
import service.PatientService;

import java.util.List;
import java.util.Scanner;
public class HospitalManagementApp {
    public static Scanner scanner = new Scanner(System.in);
    public static Integer mainMenuOption = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Hospital Management System");
        while (mainMenuOption != 8){
            showMainMenu();
            mainMenuOption = scanner.nextInt();
            scanner.nextLine();
            switch (mainMenuOption) {
                case 1 -> showPatientManagementMenu();
                case 2 -> showDoctorManagementMenu();
                case 3 -> System.out.println("Nurse Management – coming soon...");
                case 4 -> System.out.println("Appointment Management – coming soon...");
                case 5 -> System.out.println("Medical Records Management – coming soon...");
                case 6 -> System.out.println("Department Management – coming soon...");
                case 7 -> System.out.println("Reports and Statistics – coming soon...");
                case 8 -> {
                    System.out.println("Exiting the system... Goodbye!");
                    mainMenuOption = 8;
                }
                default -> System.out.println("Please enter a valid option (1–8).");
            }
        }
    }

    public static void showMainMenu() {
        System.out.println("===== Main Menu =====");
        System.out.print("""
                1- Patient Management
                2- Doctor Management
                3- Nurse Management
                4- Appointment Management
                5- Medical Records Management
                6- Department Management
                7- Reports and Statistics
                8- Exit
                """);
        System.out.println("===============");
        System.out.print("Please enter your choice: ");
    }

    private static void patientManagementMenu() {
        System.out.println("===== Main Menu =====");
        System.out.print("""
                1- Register New Patient
                2- Register InPatient
                3- Register OutPatient
                4- Register Emergency Patient
                5- View All Patients
                6- Search Patient
                7- Update Patient Information
                8- Remove Patient
                9- View Patient Medical History
                10- Exit
                """);
        System.out.println("===============");
        System.out.print("Please enter your choice: ");
    }

    private static void showPatientManagementMenu() {
        int option = 0;

        while (option != 10) {
            patientManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    Patient patient = PatientService.addPatient();

                    // Ensure patient was successfully created (not null)
                    if (patient != null) {
                        PatientService.save(patient);

                        // Now, the patient is in the list and can be found by ID.
                        System.out.print("Do you want to add an allergy for this patient now? (yes/no): ");
                        String response = PatientService.scanner.nextLine().trim().toLowerCase();

                        if (response.equals("yes")) {
                            PatientService.addAllergyToPatient(patient.getId());
                        } else {
                            System.out.println("Skipping allergy addition.\n");
                        }
                    }
                }
                case 2 -> System.out.println("Register InPatient – coming soon...");
                case 3 -> System.out.println("Register OutPatient – coming soon...");
                case 4 -> System.out.println("Register Emergency Patient – coming soon...");
                case 5 -> PatientService.displayAllPatients();
                case 6 -> {
                    System.out.print("Enter patient name to search: ");
                    String name = scanner.nextLine();
                    List<Patient> results = PatientService.searchPatientsByName(name);

                    if (results.isEmpty()) {
                        System.out.println("No patients found matching \"" + name);
                    } else {
                        System.out.println("Found " + results.size() + " patient(s) matching \"" + name + "\":");
                        for (Patient p : results) {
                            System.out.println("- Name: " + p.getFirstName() + p.getLastName() + " (ID: " + p.getId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 7 -> {
                    System.out.print("Enter patient ID to update: ");
                    String id = scanner.nextLine();
                    Patient updatedPatient = PatientService.addPatient(); // reuse input method
                    PatientService.editPatient(id, updatedPatient);
                }
                case 8 -> {
                    System.out.print("Enter patient ID to remove: ");
                    String id = scanner.nextLine();
                    PatientService.removePatient(id);
                }
                case 9 -> System.out.println("View Patient Medical History – coming soon...");
                case 10 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Please enter a valid option (1–10).");
            }
        }
    }

    private static void doctorManagementMenu() {
        System.out.println("===== Main Menu =====");
        System.out.print("""
                1- Add Doctor
                2- Add Surgeon
                3- Add Consultant
                4- Add General Practitioner
                5- View All Doctors
                6- Search Doctor by Specialization
                7- View Available Doctors
                8- Assign Patient to Doctor
                9- Update Doctor Information
                10- Remove Doctor
                11- Exit
                """);
        System.out.println("===============");
        System.out.print("Please enter your choice: ");
    }

    private static void showDoctorManagementMenu() {
        int option = 0;

        while (option != 10) {
            patientManagementMenu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    Patient patient = PatientService.addPatient();

                    if (patient != null) {
                        // 1. Ask about allergy BEFORE saving the patient to the list
                        System.out.print("Do you want to add an allergy for this patient now? (yes/no): ");
                        String response = PatientService.scanner.nextLine().trim().toLowerCase();

                        if (response.equals("yes")) {
                            PatientService.addAllergyToPatient(patient.getId());
                        } else {
                            System.out.println("Skipping allergy addition.\n");
                        }
                    }

                    // 2. Save the patient (with or without the new allergy)
                    PatientService.save(patient);
                }
                case 2 -> System.out.println("Register InPatient – coming soon...");
                case 3 -> System.out.println("Register OutPatient – coming soon...");
                case 4 -> System.out.println("Register Emergency Patient – coming soon...");
                case 5 -> PatientService.displayAllPatients();
                case 6 -> {
                    System.out.print("Enter patient name to search: ");
                    String name = scanner.nextLine();
                    List<Patient> results = PatientService.searchPatientsByName(name);

                    if (results.isEmpty()) {
                        System.out.println("No patients found matching \"" + name);
                    } else {
                        System.out.println("Found " + results.size() + " patient(s) matching \"" + name + "\":");
                        for (Patient p : results) {
                            System.out.println("- Name: " + p.getFirstName() + p.getLastName() + " (ID: " + p.getId() + ")");
                        }
                    }
                    System.out.println();
                }
                case 7 -> {
                    System.out.print("Enter patient ID to update: ");
                    String id = scanner.nextLine();
                    Patient updatedPatient = PatientService.addPatient(); // reuse input method
                    PatientService.editPatient(id, updatedPatient);
                }
                case 8 -> {
                    System.out.print("Enter patient ID to remove: ");
                    String id = scanner.nextLine();
                    PatientService.removePatient(id);
                }
                case 9 -> System.out.println("View Patient Medical History – coming soon...");
                case 10 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Please enter a valid option (1–10).");
            }
        }
    }
}
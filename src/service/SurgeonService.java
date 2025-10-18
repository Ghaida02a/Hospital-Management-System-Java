package service;

import entity.Surgeon;

import java.util.ArrayList;
import java.util.List;

import static service.DoctorService.scanner;

public class SurgeonService {
    public static Surgeon addSurgeon() {
        Surgeon surgeon = new Surgeon();

        System.out.print("Enter Surgeon ID: ");
        String idInput = scanner.nextLine();
        surgeon.setId(idInput);
        surgeon.setDoctorId(idInput);

        System.out.print("Enter First Name: ");
        surgeon.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        surgeon.setLastName(scanner.nextLine());

        System.out.print("Enter Gender: ");
        surgeon.setGender(scanner.nextLine());

        System.out.print("Enter Phone Number: ");
        surgeon.setPhoneNumber(scanner.nextLine());

        System.out.print("Enter Email: ");
        surgeon.setEmail(scanner.nextLine());

        System.out.print("Enter Address: ");
        surgeon.setAddress(scanner.nextLine());

        System.out.print("Enter Specialization: ");
        surgeon.setSpecialization(scanner.nextLine());

        System.out.print("Enter Qualification: ");
        surgeon.setQualification(scanner.nextLine());

        boolean validExp = false;
        while (!validExp) {
            System.out.print("Enter Experience Years (integer) or leave blank for 0: ");
            String expInput = scanner.nextLine().trim();
            if (expInput.isEmpty()) {
                surgeon.setExperienceYears(0);
                validExp = true;
            } else if (expInput.matches("\\d+")) {
                surgeon.setExperienceYears(Integer.parseInt(expInput));
                validExp = true;
            } else {
                System.out.println("Invalid number. Please enter an integer.");
            }
        }

        System.out.print("Enter Department ID: ");
        surgeon.setDepartmentId(scanner.nextLine());

        boolean validFee = false;
        while (!validFee) {
            System.out.print("Enter Consultation Fee (number) or leave blank for 0: ");
            String feeInput = scanner.nextLine().trim();
            if (feeInput.isEmpty()) {
                surgeon.setConsultationFee(0.0);
                validFee = true;
            } else if (feeInput.matches("\\d+(\\.\\d+)?")) {
                surgeon.setConsultationFee(Double.parseDouble(feeInput));
                validFee = true;
            } else {
                System.out.println("Invalid number. Please enter a valid fee (e.g., 50.0).");
            }
        }

        // Available slots
        System.out.print("Enter available slots as comma-separated integers (0-23) or leave blank: ");
        String slotsInput = scanner.nextLine().trim();
        List<Integer> slots = new ArrayList<>();
        if (!slotsInput.isEmpty()) {
            String[] parts = slotsInput.split(",");
            // Use a boolean bitmap for seen values (0..23) to deduplicate while preserving first-seen order
            boolean[] seen = new boolean[24];
            for (String p : parts) {
                String t = p.trim();
                if (t.matches("\\d+")) {
                    int val = Integer.parseInt(t);
                    if (val < 0 || val > 23) {
                        System.out.println("Warning: invalid slot '" + t + "' ignored (expected 0-23).");
                        continue;
                    }
                    if (!seen[val]) {
                        seen[val] = true;
                        slots.add(val);
                    }
                } else {
                    System.out.println("Warning: invalid slot '" + p + "' ignored.");
                }
            }
        }
        surgeon.setAvailableSlots(slots);

        // Surgeon-specific fields
        System.out.print("Enter surgery types (comma-separated) or leave blank: ");
        String typesInput = scanner.nextLine().trim();
        if (!typesInput.isEmpty()) {
            String[] types = typesInput.split(",");
            for (String t : types) {
                String tri = t.trim();
                if (!tri.isEmpty()) surgeon.addSurgeryType(tri);
            }
        }

        // New: surgeriesPerformed
        boolean validSurgeries = false;
        while (!validSurgeries) {
            System.out.print("Enter number of surgeries performed (integer) or leave blank for 0: ");
            String sInput = scanner.nextLine().trim();
            if (sInput.isEmpty()) {
                surgeon.setSurgeriesPerformed(0);
                validSurgeries = true;
            } else if (sInput.matches("\\d+")) {
                surgeon.setSurgeriesPerformed(Integer.parseInt(sInput));
                validSurgeries = true;
            } else {
                System.out.println("Invalid number. Please enter a non-negative integer.");
            }
        }

        // operationTheatreAccess
        System.out.print("Operation Theatre Access? (true/false, yes/no) or press Enter for 'false': ");
        String otInput = scanner.nextLine().trim();
        boolean otAccess = false;
        if (!otInput.isEmpty()) {
            String low = otInput.toLowerCase();
            otAccess = low.equals("true") || low.equals("t") || low.equals("yes") || low.equals("y") || low.equals("1");
        }
        surgeon.setOperationTheatreAccess(otAccess);

        //surgeon.setAssignedPatients(new ArrayList<>());

        System.out.print("Enter Availability (true/false or yes/no) or press Enter to infer from provided slots: ");
        String availInput = scanner.nextLine().trim();
        boolean availability;
        if (availInput.isEmpty()) {
            availability = !slots.isEmpty();
        } else {
            String a = availInput.toLowerCase();
            availability = a.equals("true") || a.equals("t") || a.equals("yes") || a.equals("y") || a.equals("1");
        }
        surgeon.setAvailable(availability);
        return surgeon;
    }

}

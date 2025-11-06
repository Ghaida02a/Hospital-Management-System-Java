package service;

import Interface.Manageable;
import Interface.Searchable;
import Utils.HelperUtils;
import Utils.InputHandler;
import entity.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DoctorService implements Manageable, Searchable {
    public static List<Doctor> doctorsList = new ArrayList<>();
    public static List<Surgeon> surgeonList = new ArrayList<>();
    public static List<Consultant> consultantList = new ArrayList<>();
    public static List<GeneralPractitioner> generalPractitionerList = new ArrayList<>();

    private static Doctor initializeDoctor(Doctor doctor) {
        // Generate ID
        String generatedId;
        do {
            generatedId = HelperUtils.generateId("DR");
        }
        while (HelperUtils.checkIfIdExists(doctorsList, generatedId)); // ensure uniqueness
        doctor.setDoctorId(generatedId);
        System.out.println("Doctor ID: " + doctor.getDoctorId());

        return doctor;
    }

    public static Doctor addDoctor() {
        Doctor doctor = new Doctor();
        System.out.println("\n--- Doctor Registration ---");
        doctor.setId(HelperUtils.getRandomNumber(10));
        System.out.println("ID: " + doctor.getId());
        initializeDoctor(doctor);

        doctor.setFirstName(InputHandler.getStringInput("Enter First Name: "));
        doctor.setLastName(InputHandler.getStringInput("Enter Last Name: "));

        doctor.setDateOfBirth(InputHandler.getDateInput("Enter Date of Birth"));

        doctor.setGender(InputHandler.getGenderInput("Enter Gender: "));

        doctor.setPhoneNumber(InputHandler.getPhoneNumberInput("Enter Phone Number: "));

        doctor.setEmail(InputHandler.getEmailInput("Enter Email: "));

        doctor.setAddress(InputHandler.getStringInput("Enter Address: "));

        doctor.setSpecialization(InputHandler.getStringInput("Enter Specialization: "));

        doctor.setQualification(InputHandler.getStringInput("Enter Qualification: "));

        doctor.setExperienceYears(InputHandler.getIntInput("Enter Experience Years: "));

        doctor.setDepartmentId(InputHandler.getStringInput("Enter Department ID: "));

        doctor.setConsultationFee(InputHandler.getDoubleInput("Enter Consultation Fee: "));


        // Available slots (comma-separated integers 0-23)
//        System.out.print("Enter available slots as comma-separated integers (0-23) or leave blank: ");
        String slotsInput = InputHandler.getStringInput("Enter available slots (comma-separated 0-23): ");
        List<Integer> slots = new ArrayList<>();
        if (!slotsInput.isEmpty()) {
            String[] parts = slotsInput.split(",");
            Set<Integer> seen = new LinkedHashSet<>();
            for (String p : parts) {
                try {
                    int val = Integer.parseInt(p.trim());
                    if (val >= 0 && val <= 23) seen.add(val);
                    else System.out.println("Warning: invalid slot '" + val + "' ignored.");
                } catch (NumberFormatException e) {
                    System.out.println("Warning: invalid slot '" + p + "' ignored.");
                }
            }
            slots.addAll(seen);
        }
        doctor.setAvailableSlots(slots);

//        doctor.setAssignedPatients(new ArrayList<>());

        doctor.setAvailable(InputHandler.getConfirmation("Is the doctor available?"));
        return doctor;
    }

    public static Doctor addDoctor(String firstName, String lastName, String specialization, String phone) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setSpecialization(specialization);
        doctor.setPhoneNumber(phone);
        return initializeDoctor(doctor);
    }

    public static Doctor addDoctor(String name, String specialization, String phone, double consultationFee) {
        Doctor doctor = new Doctor();
        String[] nameParts = name.trim().split(" ");
        if (nameParts.length >= 2) {
            doctor.setFirstName(nameParts[0]);
            doctor.setLastName(nameParts[1]);
        } else if (nameParts.length == 1) {
            doctor.setFirstName(nameParts[0]);
            doctor.setLastName("");
        } else {
            doctor.setFirstName("Unknown");
            doctor.setLastName("Unknown");
        }
        doctor.setSpecialization(specialization);
        doctor.setPhoneNumber(phone);
        doctor.setConsultationFee(consultationFee);
        return initializeDoctor(doctor);
    }

    public static Doctor addDoctor(Doctor doctor) {
        return initializeDoctor(doctor);
    }

    public static Doctor assignPatient(String doctorId, String patientId) {
        Doctor found = getDoctorById(doctorId);
        if (found == null) {
            System.out.println("Doctor with ID " + doctorId + " not found.");
            return null;
        }

        entity.Patient patient = PatientService.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient with ID " + patientId + " not found.");
            return null;
        }

        boolean assigned = found.assignPatient(patient);
        if (assigned) {
            System.out.println("Patient " + patientId + " assigned to Doctor " + doctorId + ".");
        } else {
            System.out.println("Failed to assign patient " + patientId + " to Doctor " + doctorId + ".");
        }
        return found;
    }

    public static Doctor assignPatient(Doctor doctor, Patient patient) {
        boolean assigned = doctor.assignPatient(patient);
        if (assigned) {
            System.out.println("Patient " + patient.getId() + " assigned to Doctor " + doctor.getId() + ".");
        } else {
            System.out.println("Failed to assign patient " + patient.getId() + " to Doctor " + doctor.getId() + ".");
        }
        return doctor;
    }

    public static Doctor assignPatient(String doctorId, List<String> patientIds) {
        Doctor found = getDoctorById(doctorId);
        if (found == null) {
            System.out.println("Doctor with ID " + doctorId + " not found.");
            return null;
        }

        for (String patientId : patientIds) {
            entity.Patient patient = PatientService.getPatientById(patientId);
            if (patient == null) {
                System.out.println("Patient with ID " + patientId + " not found. Skipping.");
                continue;
            }

            boolean assigned = found.assignPatient(patient);
            if (assigned) {
                System.out.println("Patient " + patientId + " assigned to Doctor " + doctorId + ".");
            } else {
                System.out.println("Failed to assign patient " + patientId + " to Doctor " + doctorId + ".");
            }
        }
        return found;
    }

    public static Doctor displayDoctors() {
        displayAllDoctors();
        return null;
    }

    public static void displayDoctors(String specialization) {
        getDoctorsBySpecialization(specialization);
    }

    public static void displayDoctors(String departmentId, boolean showAvailableOnly) {
        List<Doctor> filteredDoctors = new ArrayList<>();
        for (Doctor doctor : doctorsList) {
            if (doctor.getDepartmentId().equals(departmentId)) {
                if (showAvailableOnly) {
                    if (doctor.isAvailable()) {
                        filteredDoctors.add(doctor);
                    }
                } else {
                    filteredDoctors.add(doctor);
                }
            }
        }
        if (filteredDoctors.isEmpty()) {
            System.out.println("No doctors found for Department ID: " + departmentId);
        } else {
            System.out.println("Doctors in Department ID " + departmentId + ":");
            for (Doctor doc : filteredDoctors) {
                doc.displayInfo("");
                System.out.println("------------------------");
            }
        }
    }

    public static void save(Doctor doctor) {
        if (HelperUtils.isNull(doctor)) {
            System.out.println("Cannot save null doctor.");
            return;
        }
        if (getDoctorById(doctor.getId()) != null) {
            System.out.println("Doctor with ID " + doctor.getId() + " already exists.");
            return;
        }
        if (HelperUtils.isNotNull(doctor)) {
            doctorsList.add(doctor);
            System.out.println("Doctor Added successfully!\n");
        }
    }

    public static void save(Surgeon surgeon) { //save Surgeon
        if (HelperUtils.isNotNull(surgeon)) {
            //surgeonList.add(surgeon);
            System.out.println("Surgeon Added successfully!\n");
        }
    }

    public static void save(Consultant consultant) { //save Consultant
        if (HelperUtils.isNotNull(consultant)) {
            consultantList.add(consultant);
            System.out.println("Consultant Added successfully!\n");
        }
    }

    public static void save(GeneralPractitioner generalPractitioner) { //save General Practitioner
        if (HelperUtils.isNotNull(generalPractitioner)) {
            generalPractitionerList.add(generalPractitioner);
            System.out.println("General Practitioner Added successfully!\n");
        }
    }

    public static void editDoctor(String doctorId, Doctor updatedDoctor) {
        for (int i = 0; i < doctorsList.size(); i++) {
            if (doctorsList.get(i).getDoctorId().equals(doctorId)) {
                updatedDoctor.setDoctorId(doctorId);

                // Ask to update availability
                if (InputHandler.getConfirmation("Do you want to update availability slots? ")) {
                    List<Integer> slots = InputHandler.getIntegerList("Enter available time slots (0–23): ");
                    updatedDoctor.updateAvailability(slots);
                }

                doctorsList.set(i, updatedDoctor);
                System.out.println("Doctor updated successfully.");
                return;
            }
        }
        System.out.println("Doctor with ID " + doctorId + " not found.");
    }


    public static boolean assignPatientToDoctor(String doctorId, String patientId) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            System.out.println("Invalid doctor ID.");
            return false;
        }
        if (patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Invalid patient ID.");
            return false;
        }

        Doctor found = getDoctorById(doctorId); // use new method
        if (found == null) {
            System.out.println("Doctor with ID " + doctorId + " not found.");
            return false;
        }

        entity.Patient patient = PatientService.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient with ID " + patientId + " not found.");
            return false;
        }

        boolean assigned = found.assignPatient(patient);
        if (assigned) {
            System.out.println("Patient " + patientId + " assigned to Doctor " + doctorId + ".");
        } else {
            System.out.println("Failed to assign patient " + patientId + " to Doctor " + doctorId + ".");
        }
        return assigned;
    }

    public static void removeDoctor(String doctorId) {
        Doctor found = getDoctorById(doctorId);
        if (found != null) {
            doctorsList.remove(found);
            System.out.println("Doctor with ID " + doctorId + " has been removed.");
        } else {
            System.out.println("Doctor with ID " + doctorId + " not found.");
        }
    }

    public static Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : doctorsList) {
            if (doctor.getDoctorId().equals(doctorId)) {
                return doctor;
            }
        }
        return null; //not found
    }


    public static void displayAllDoctors() {
        if (!doctorsList.isEmpty()) {
            System.out.println("===== Doctors List =====");
            for (Doctor doctor : doctorsList) {
                doctor.displayInfo();
                System.out.println("------------------------");
            }
        }

        if (!surgeonList.isEmpty()) {
            System.out.println("----- Surgeons -----");
            for (Surgeon surgeon : surgeonList) {
                surgeon.displayInfo();
                System.out.println("------------------------");
            }
        }

        if (!consultantList.isEmpty()) {
            System.out.println("----- Consultants -----");
            for (Consultant consultant : consultantList) {
                consultant.displayInfo();
                System.out.println("------------------------");
            }
        }

        if (!generalPractitionerList.isEmpty()) {
            System.out.println("----- General Practitioners -----");
            for (GeneralPractitioner gp : generalPractitionerList) {
                gp.displayInfo();
                System.out.println("------------------------");
            }
        }
    }

    public static List<Doctor> getDoctorsBySpecialization(String specialization) { //Filters doctors by specialization
        List<Doctor> specializedDoctors = new ArrayList<>();
        for (Doctor doctor : doctorsList) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                specializedDoctors.add(doctor);
            }
        }
        if (specializedDoctors.isEmpty()) {
            System.out.println("No doctors found with specialization: " + specialization);
        } else {
            System.out.println("Doctors with specialization " + specialization + ":");
            for (Doctor doc : specializedDoctors) {
                doc.displayInfo("");
                System.out.println("------------------------");
            }
        }
        return specializedDoctors;
    }

    public static List<Doctor> getAvailableDoctors() { //Returns doctors marked as available
        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctorsList) {
            if (doctor.isAvailable()) {
                availableDoctors.add(doctor);
            }
        }
        return availableDoctors;
    }

    public static Surgeon addSurgeon() {
        Surgeon surgeon = new Surgeon();
        System.out.println("\n--- Surgeon Registration ---");

        Doctor baseDoctor = DoctorService.addDoctor();// fill basic patient info

        surgeon.setId(baseDoctor.getId());
        surgeon.setFirstName(baseDoctor.getFirstName());
        surgeon.setLastName(baseDoctor.getLastName());
        surgeon.setDateOfBirth(baseDoctor.getDateOfBirth());
        surgeon.setGender(baseDoctor.getGender());
        surgeon.setPhoneNumber(baseDoctor.getPhoneNumber());
        surgeon.setEmail(baseDoctor.getEmail());
        surgeon.setAddress(baseDoctor.getAddress());
        surgeon.setSpecialization(baseDoctor.getSpecialization());
        surgeon.setQualification(baseDoctor.getQualification());
        surgeon.setExperienceYears(baseDoctor.getExperienceYears());
        surgeon.setDepartmentId(baseDoctor.getDepartmentId());
        surgeon.setConsultationFee(baseDoctor.getConsultationFee());
        surgeon.setAvailableSlots(baseDoctor.getAvailableSlots());
        surgeon.setAvailable(baseDoctor.isAvailable());

        // Surgeon-specific info
        surgeon.setSurgeriesPerformed(InputHandler.getIntInput("Enter Number of Surgeries Performed: "));
        surgeon.setSurgeryTypeForSurgeon(surgeon);
        surgeon.setOperationTheatreAccess(InputHandler.getConfirmation("Does the surgeon have Operation Theatre Access? "));

        return surgeon;
    }

    public static Consultant addConsultant() {
        Consultant consultant = new Consultant();
        System.out.println("\n--- Consultant Registration ---");

        Doctor baseDoctor = DoctorService.addDoctor();// fill basic patient info

        consultant.setId(baseDoctor.getId());
        consultant.setFirstName(baseDoctor.getFirstName());
        consultant.setLastName(baseDoctor.getLastName());
        consultant.setDateOfBirth(baseDoctor.getDateOfBirth());
        consultant.setGender(baseDoctor.getGender());
        consultant.setPhoneNumber(baseDoctor.getPhoneNumber());
        consultant.setEmail(baseDoctor.getEmail());
        consultant.setAddress(baseDoctor.getAddress());
        consultant.setSpecialization(baseDoctor.getSpecialization());
        consultant.setQualification(baseDoctor.getQualification());
        consultant.setExperienceYears(baseDoctor.getExperienceYears());
        consultant.setDepartmentId(baseDoctor.getDepartmentId());
        consultant.setConsultationFee(baseDoctor.getConsultationFee());
        consultant.setAvailableSlots(baseDoctor.getAvailableSlots());
        consultant.setAvailable(baseDoctor.isAvailable());

        // Consultant-specific info
        //   private boolean onlineConsultationAvailable;
        //    private int consultationDuration; // in minutes
        //consultant.setConsultationTypes(InputHandler.getStringInput(""));
        consultant.setConsultationDuration(InputHandler.getIntInput("Enter Consultation Duration (in minutes)"));
        save(consultant);
        return consultant;
    }

    public static GeneralPractitioner addGeneralPractitioner() {
        GeneralPractitioner generalPractitioner = new GeneralPractitioner();
        System.out.println("\n--- General Practitioner Registration ---");

        Doctor baseDoctor = DoctorService.addDoctor();// fill basic patient info

        generalPractitioner.setId(baseDoctor.getId());
        generalPractitioner.setFirstName(baseDoctor.getFirstName());
        generalPractitioner.setLastName(baseDoctor.getLastName());
        generalPractitioner.setDateOfBirth(baseDoctor.getDateOfBirth());
        generalPractitioner.setGender(baseDoctor.getGender());
        generalPractitioner.setPhoneNumber(baseDoctor.getPhoneNumber());
        generalPractitioner.setEmail(baseDoctor.getEmail());
        generalPractitioner.setAddress(baseDoctor.getAddress());
        generalPractitioner.setSpecialization(baseDoctor.getSpecialization());
        generalPractitioner.setQualification(baseDoctor.getQualification());
        generalPractitioner.setExperienceYears(baseDoctor.getExperienceYears());
        generalPractitioner.setDepartmentId(baseDoctor.getDepartmentId());
        generalPractitioner.setConsultationFee(baseDoctor.getConsultationFee());
        generalPractitioner.setAvailableSlots(baseDoctor.getAvailableSlots());
        generalPractitioner.setAvailable(baseDoctor.isAvailable());

        // generalPractitioner-specific info
        generalPractitioner.setWalkinAvailable(InputHandler.getConfirmation("Is Walk-in Available? "));
        generalPractitioner.setHomeVisitAvailable(InputHandler.getConfirmation("Is Home Visit Available? "));
        generalPractitioner.setVaccinationCertified(InputHandler.getConfirmation("Is Vaccination Certified? "));

        return generalPractitioner;
    }

    public static List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctorsList);
    }

    @Override
    public String add(Object entity) {
        if (entity instanceof Doctor doctor) {
            save(doctor);
            return "Doctor added successfully: " + doctor.getId();
        }
        return "Invalid entity type.";
    }

    @Override
    public String remove(String id) {
        Doctor doctor = getDoctorById(id);
        if (doctor != null) {
            doctorsList.remove(doctor);
            return "Doctor " + id + " removed successfully.";
        }
        return "Doctor not found.";
    }

    @Override
    public String getAll() {
        if (doctorsList.isEmpty()) return "No doctors found.";
        StringBuilder sb = new StringBuilder("===== All Doctors =====\n");
        for (Doctor d : doctorsList) {
            sb.append(d.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String search(String keyword) {
        StringBuilder sb = new StringBuilder();
        for (Doctor d : doctorsList) {
            if (d.getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
                    d.getLastName().toLowerCase().contains(keyword.toLowerCase()) ||
                    d.getSpecialization().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(d.toString()).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No matches found for keyword: " + keyword;
    }

    @Override
    public String searchById(String id) {
        Doctor doctor = getDoctorById(id);
        return (doctor != null) ? doctor.toString() : "Doctor not found with ID: " + id;
    }

}

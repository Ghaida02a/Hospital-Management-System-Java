package service;

import Interface.Manageable;
import Interface.Searchable;
import Utils.HelperUtils;
import Utils.InputHandler;
import entity.Department;
import entity.Doctor;
import entity.Nurse;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService implements Manageable, Searchable {
    public static List<Department> departmentList = new ArrayList<>();

    public static Department addDepartment() {
        Department department = new Department();
        System.out.println("\n--- Department Registration ---");
        String generatedId = HelperUtils.getRandomNumber(4);
        department.setDepartmentId(generatedId);
        System.out.println("Department ID: " + department.getDepartmentId());

        department.setDepartmentName(InputHandler.getStringInput("Enter Department Name: "));

        department.setHeadDoctorId(InputHandler.getStringInput("Enter Head Doctor ID: "));

        int bedCapacity = InputHandler.getIntInput("Enter bed capacity: ");

        int availableBeds = InputHandler.getIntInput("Enter available beds: ");

        if (bedCapacity < 0) bedCapacity = 0;
        if (availableBeds < 0) availableBeds = 0;
        if (availableBeds > bedCapacity) availableBeds = bedCapacity;

        department.setBedCapacity(bedCapacity);
        department.setAvailableBeds(availableBeds);
        return department;
    }

    public static boolean saveDepartment(Department dept) {
        if (HelperUtils.isNull(dept) || HelperUtils.isNull(dept.getDepartmentId()) || dept.getDepartmentId().trim().isEmpty()) {
            System.out.println("Department or departmentId must not be null/empty");
            return false;
        }
        if (getDepartmentById(dept.getDepartmentId()) != null) {
            System.out.println("Department ID already exists: " + dept.getDepartmentId());
            return false;
        }
        departmentList.add(dept);
        System.out.println("Department saved successfully.");
        return true;
    }

    public static Department getDepartmentById(String departmentId) {
        if (HelperUtils.isNull(departmentId)) {
            return null;
        }
        for (Department department : departmentList) {
            if (departmentId.equals(department.getDepartmentId())) {
                return department;
            }
        }
        return null;
    }

    public static void displayAllDepartments() {
        if (departmentList.isEmpty()) {
            System.out.println("No departments available.");
            return;
        }
        for (Department dept : departmentList) {
            System.out.println("Department ID: " + dept.getDepartmentId());
            System.out.println("Department Name: " + dept.getDepartmentName());
            System.out.println("Head Doctor ID: " + dept.getHeadDoctorId());
            System.out.println("Bed Capacity: " + dept.getBedCapacity());
            System.out.println("Available Beds: " + dept.getAvailableBeds());
            System.out.println("---------------------------");
        }
    }

    public static boolean updateDepartment(String departmentId, Department updated) {
        if (HelperUtils.isNull(departmentId) || HelperUtils.isNull(updated)) return false;
        for (int i = 0; i < departmentList.size(); i++) {
            Department d = departmentList.get(i);
            if (departmentId.equals(d.getDepartmentId())) {
                d.setDepartmentName(updated.getDepartmentName());
                d.setHeadDoctorId(updated.getHeadDoctorId());
                d.setBedCapacity(updated.getBedCapacity());
                d.setAvailableBeds(updated.getAvailableBeds());
                departmentList.set(i, d);
                System.out.println("Department " + departmentId + " updated.");
                return true;
            }
        }
        System.out.println("Department " + departmentId + " not found.");
        return false;
    }

    public static boolean deleteDepartment(String departmentId) {
        if (departmentId == null) return false;
        boolean removed = departmentList.removeIf(d -> departmentId.equals(d.getDepartmentId()));
        if (removed) System.out.println("Department " + departmentId + " deleted.");
        else System.out.println("Department " + departmentId + " not found.");
        return removed;
    }

    // Assign a doctor (by doctorId) to a department (by departmentId)
    public static boolean assignDoctorToDepartment(String doctorId, String departmentId) {
        Department department = getDepartmentById(departmentId);
        Doctor doctor = DoctorService.getDoctorById(doctorId);

        if (HelperUtils.isNull(department) || HelperUtils.isNull(doctor)) {
            System.out.println("Doctor or department not found.");
            return false;
        }

        department.assignDoctor(doctor);
        doctor.setDepartmentId(departmentId);
        return true;
    }

    public static boolean assignNurseToDepartment(String nurseId, String departmentId) {
        Department department = getDepartmentById(departmentId);
        Nurse nurse = NurseService.getNurseById(nurseId);

        if (HelperUtils.isNull(department) || HelperUtils.isNull(nurse)) {
            System.out.println("Nurse or department not found.");
            return false;
        }

        department.assignNurse(nurse);
        nurse.setDepartmentId(departmentId);
        return true;
    }

    public static boolean updateBedAvailability(String departmentId, int beds) {
        Department department = getDepartmentById(departmentId);
        if (HelperUtils.isNull(department)) {
            System.out.println("Department not found.");
            return false;
        }

        department.updateBedAvailability(beds);
        return true;
    }

    public static List<Department> getAllDepartments() {
        return new ArrayList<>(departmentList);
    }

    public static void displayDepartmentNamesAndIds() {
        if (departmentList.isEmpty()) {
            System.out.println("No Department found.\n");
            return;
        }

        System.out.println("===== Department List =====");
        for (Department department : departmentList) {
            System.out.println("Department ID: " + department.getDepartmentId() + "  Department Name:" + department.getDepartmentName());
        }
        System.out.println("========================\n");
    }

    @Override
    public String add(Object entity) {
        if (entity instanceof Department) {
            departmentList.add((Department) entity);
            return "Department added successfully!";
        }
        return "Invalid entity type.";
    }

    @Override
    public String remove(String id) {
        Department department = getDepartmentById(id);
        if (HelperUtils.isNotNull(department)) {
            departmentList.remove(department);
            return "Department with ID " + id + " removed successfully!";
        }
        return "Department not found.";
    }

    @Override
    public String getAll() {
        if (departmentList.isEmpty()) {
            return "No departments available.";
        }
        StringBuilder sb = new StringBuilder("===== Department List =====\n");
        for (Department department : departmentList) {
            sb.append(department.displayInfo(""));
            sb.append(System.lineSeparator());
            sb.append("---------------------------\n");
        }
        return sb.toString();
    }

    @Override
    public String search(String keyword) {
        StringBuilder sb = new StringBuilder();
        for (Department d : departmentList) {
            if (d.getDepartmentName().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(d.displayInfo(""));
                sb.append(System.lineSeparator());
            }
        }
        return sb.length() > 0 ? sb.toString() : "No departments found for: " + keyword;
    }

    @Override
    public String searchById(String id) {
        Department dept = getDepartmentById(id);
        if (HelperUtils.isNotNull(dept)) {
            return dept.displayInfo("");
        }
        return "Department not found: " + id;
    }

    public static void addSampleDepartments() {
        String[] departmentNames = {"Cardiology", "Orthopedics",
                "Neurology", "Gastroenterology",
                "Endocrinology", "Pulmonology",
                "Nephrology", "Oncology",
                "Dermatology", "Psychiatry",
                "Pediatrics", "Geriatrics",
                "General Surgery", "Emergency",
                "Trauma Center", "Pharmacy",
                "Physiotherapy",
        };

        for (int i = 0; i < 4; i++) {
            Department department = new Department();
            department.setDepartmentId(HelperUtils.getRandomNumber(4));
            department.setDepartmentName(departmentNames[i]);
            department.setHeadDoctorId("DR-123" + i);
            department.setDoctors(new ArrayList<>());
            department.setNurses(new ArrayList<>());
            department.setBedCapacity(50);
            department.setAvailableBeds(30);

            saveDepartment(department);
        }
    }
}
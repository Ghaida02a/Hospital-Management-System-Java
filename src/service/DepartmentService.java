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
import java.util.Scanner;

public class DepartmentService implements Manageable, Searchable {
    public static List<Department> departmentList = new ArrayList<>();

    public static Department addDepartment() {
        Department department = new Department();
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
        if (dept == null || dept.getDepartmentId() == null || dept.getDepartmentId().trim().isEmpty()) {
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
        if (departmentId == null) {
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
        if (departmentId == null || updated == null) return false;
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
    public static boolean assignDoctorToDepartment(String departmentId, String doctorId) {
        if (doctorId == null || doctorId.isEmpty()) {
            System.out.println("Invalid doctor ID.");
            return false;
        }
        if (departmentId == null || departmentId.isEmpty()) {
            System.out.println("Invalid department ID.");
            return false;
        }

        Department dept = getDepartmentById(departmentId);
        if (dept == null) {
            System.out.println("Department not found: " + departmentId);
            return false;
        }

        Doctor found = null;
        for (Doctor doc : service.DoctorService.doctorsList) {
            if (doc != null && doctorId.equals(doc.getId())) {
                found = doc;
                break;
            }
        }
        if (found == null) {
            System.out.println("Doctor not found: " + doctorId);
            return false;
        }

        // Ensure department's doctor list exists
        List<Doctor> docs = dept.getDoctors();
        if (docs == null) {
            docs = new ArrayList<>();
            dept.setDoctors(docs);
        }

        // Check if doctor already assigned to this department
        for (Doctor d : docs) {
            if (d != null && doctorId.equals(d.getId())) {
                System.out.println("Doctor " + doctorId + " is already assigned to department " + departmentId);
                return false;
            }
        }

        // Assign
        docs.add(found);
        found.setDepartmentId(departmentId);
        System.out.println("Doctor " + doctorId + " assigned to department " + departmentId);
        return true;
    }

    public static boolean assignNurseToDepartment(String departmentId, String nurseId) {
        if (nurseId == null || nurseId.isEmpty()) {
            System.out.println("Invalid nurse ID.");
            return false;
        }
        if (departmentId == null || departmentId.isEmpty()) {
            System.out.println("Invalid department ID.");
            return false;
        }

        Department dept = getDepartmentById(departmentId);
        if (dept == null) {
            System.out.println("Department not found: " + departmentId);
            return false;
        }

        // Find nurse from NurseService
        Nurse found = null;
        for (Nurse nurse : NurseService.nurseList) {
            if (nurse != null && nurseId.equals(nurse.getId())) {
                found = nurse;
                break;
            }
        }
        if (found == null) {
            System.out.println("Nurse not found: " + nurseId);
            return false;
        }

        // Ensure department's doctor list exists
        List<Nurse> nurse = dept.getNurses();
        if (nurse == null) {
            nurse = new ArrayList<>();
            dept.setNurses(nurse);
        }

        // Check if doctor already assigned to this department
        for (Nurse n : nurse) {
            if (n != null && nurseId.equals(n.getId())) {
                System.out.println("Nurse " + nurseId + " is already assigned to department " + departmentId);
                return false;
            }
        }

        // Assign
        nurse.add(found);
        found.setDepartmentId(departmentId);
        System.out.println("Nurse " + nurseId + " assigned to department " + departmentId);
        return true;
    }

    public static List<Department> getAllDepartments() {
        return new ArrayList<>(departmentList);
    }

    @Override
    public String add(Object entity) {
        if (entity instanceof Department) {
            Department dept = (Department) entity;
            if (saveDepartment(dept)) {
                return "Department added successfully: " + dept.getDepartmentId();
            } else {
                return "Failed to add department.";
            }
        }
        return "Invalid entity type.";
    }

    @Override
    public String remove(String id) {
        return deleteDepartment(id) ? "Department deleted: " + id : "Department not found.";
    }

    @Override
    public String getAll() {
        if (departmentList.isEmpty()) {
            return "No departments available.";
        }
        StringBuilder sb = new StringBuilder();
        for (Department d : departmentList) {
            sb.append(d.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String search(String keyword) {
        StringBuilder sb = new StringBuilder();
        for (Department d : departmentList) {
            if (d.getDepartmentName().toLowerCase().contains(keyword.toLowerCase())) {
                sb.append(d.toString()).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No departments found for: " + keyword;
    }

    @Override
    public String searchById(String id) {
        Department dept = getDepartmentById(id);
        return dept != null ? dept.toString() : "Department not found: " + id;
    }

}
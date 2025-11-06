package entity;

import Interface.Displayable;
import Utils.HelperUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Department implements Displayable {
    private String departmentId;
    private String departmentName;
    private String headDoctorId;
    private List<Doctor> doctors;
    private List<Nurse> nurses;
    private int bedCapacity;
    private int availableBeds;

    public Department() {
        this.departmentId = HelperUtils.generateId("Dep");
        this.departmentName = "";
        this.headDoctorId = "";
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
        this.bedCapacity = 0;
        this.availableBeds = 0;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        if (HelperUtils.isNull(departmentId) || departmentId.isEmpty()) {
            System.out.println("Department ID cannot be empty.");
        } else {
            this.departmentId = HelperUtils.getRandomNumber(4);
        }
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        if (HelperUtils.isNull(departmentName) || departmentName.isEmpty()) {
            System.out.println("Department name cannot be empty.");
        } else {
            this.departmentName = departmentName;
        }
    }

    public String getHeadDoctorId() {
        return headDoctorId;
    }

    public void setHeadDoctorId(String headDoctorId) {
        if (HelperUtils.isNull(headDoctorId) || headDoctorId.isEmpty()) {
            System.out.println("Head Doctor ID cannot be empty.");
        } else {
            this.headDoctorId = headDoctorId;
        }
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        if (HelperUtils.isNull(doctors) || doctors.isEmpty()) {
            System.out.println("Doctors list cannot be null or empty.");
        } else {
            this.doctors = doctors;
        }
    }

    public List<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(List<Nurse> nurses) {
        if (HelperUtils.isNull(nurses) || nurses.isEmpty()) {
            System.out.println("Nurses list cannot be null or empty.");
        } else {
            this.nurses = nurses;
        }
    }

    public int getBedCapacity() {
        return bedCapacity;
    }

    public void setBedCapacity(int bedCapacity) {
        if (HelperUtils.isPositive(bedCapacity)) {
            this.bedCapacity = bedCapacity;
        } else {
            System.out.println("Bed capacity must be a positive integer.");
        }
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        if (HelperUtils.isPositive(availableBeds) && availableBeds <= bedCapacity) {
            this.availableBeds = availableBeds;
        } else {
            System.out.println("Available beds must be between 0 and bed capacity.");
        }
    }

    public void assignDoctor(Doctor doctor) {
        if (HelperUtils.isNull(doctor)) {
            System.out.println("Cannot assign a null doctor.");
            return;
        }
        if (HelperUtils.isNull(doctors)) {
            doctors = new ArrayList<>();
        }
        if (!doctors.contains(doctor)) {
            doctors.add(doctor);
            System.out.println("Doctor " + doctor.getDoctorId() + " assigned to department " + departmentId);
        } else {
            System.out.println("Doctor " + doctor.getDoctorId() + " is already assigned.");
        }
    }

    public void assignNurse(Nurse nurse) {
        if (HelperUtils.isNull(nurse)) {
            System.out.println("Cannot assign a null nurse.");
            return;
        }
        if (HelperUtils.isNull(nurses)) {
            nurses = new ArrayList<>();
        }
        if (!nurses.contains(nurse)) {
            nurses.add(nurse);
            System.out.println("Nurse " + nurse.getNurseId() + " assigned to department " + departmentId);
        } else {
            System.out.println("Nurse " + nurse.getNurseId() + " is already assigned.");
        }
    }

    public void updateBedAvailability(int beds) {
        if (HelperUtils.isPositive(beds) && beds <= bedCapacity) {
            availableBeds = beds;
            System.out.println("Available beds updated to " + beds + " out of " + bedCapacity);
        } else {
            System.out.println("Invalid bed availability. Must be between 1 and " + bedCapacity);
        }
    }

    @Override
    public String displayInfo(String str) {
        String info = "Department ID: " + departmentId + System.lineSeparator()
                + "Department Name: " + departmentName + System.lineSeparator()
                + "Head Doctor ID: " + headDoctorId + System.lineSeparator()
                + "Number of Doctors: " + (doctors != null ? doctors.size() : 0) + System.lineSeparator()
                + "Number of Nurses: " + (nurses != null ? nurses.size() : 0) + System.lineSeparator()
                + "Bed Capacity: " + bedCapacity + System.lineSeparator()
                + "Available Beds: " + availableBeds;
        System.out.println(info);
        return info;
    }

    @Override
    public String displaySummary(String str) {
        return "Department Id" + departmentId + " - " + "Department Name" + departmentName;
    }
}

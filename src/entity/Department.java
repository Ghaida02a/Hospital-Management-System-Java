package entity;

import Interface.Displayable;

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
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.headDoctorId = headDoctorId;
        this.doctors = doctors;
        this.nurses = nurses;
        this.bedCapacity = bedCapacity;
        this.availableBeds = availableBeds;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHeadDoctorId() {
        return headDoctorId;
    }

    public void setHeadDoctorId(String headDoctorId) {
        this.headDoctorId = headDoctorId;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(List<Nurse> nurses) {
        this.nurses = nurses;
    }

    public int getBedCapacity() {
        return bedCapacity;
    }

    public void setBedCapacity(int bedCapacity) {
        this.bedCapacity = bedCapacity;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
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
    public String displaySummary(String str){
        return "Department Id" + departmentId + " - " + "Department Name" + departmentName;
    }
}
// assignDoctor(), assignNurse(), updateBedAvailability()
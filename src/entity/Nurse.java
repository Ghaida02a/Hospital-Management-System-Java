package entity;

import Interface.Displayable;

import java.time.LocalDate;
import java.util.List;

public class Nurse extends Person implements Displayable{
    private String nurseId;
    private String departmentId;
    private String shift; //- Morning/Evening/Night)
    private String qualification;
    private List<Patient> assignedPatients;

    public Nurse() {
        super();
    }

    public Nurse(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
    }

    public Nurse(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String departmentId, String shift, String qualification, List<Patient> assignedPatients) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        this.departmentId = departmentId;
        this.shift = shift;
        this.qualification = qualification;
        this.assignedPatients = assignedPatients;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(List<Patient> assignedPatients) {
        this.assignedPatients = assignedPatients;
    }

    // Displayable methods
    @Override
    public String displayInfo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo("")).append(System.lineSeparator());
        sb.append("Department Id: ").append(departmentId).append(System.lineSeparator());
        sb.append("Shift: ").append(shift).append(System.lineSeparator());
        sb.append("Qualification: ").append(qualification).append(System.lineSeparator());
        sb.append("Assigned Patients Count: ").append(assignedPatients == null ? 0 : assignedPatients.size());
        String out = sb.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "Nurse{" + getId() + ": " + getFirstName() + " " + getLastName() + "}";
    }

    //• Additional: getNursesByDepartment(), getNursesByShift()
}

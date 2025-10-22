package entity;

import Interface.Displayable;
import Utils.HelperUtils;

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
        this.nurseId = HelperUtils.generateId("NuR"); // generate nurse ID
        this.shift = "Morning"; // default shift
    }

    public Nurse(String id, String firstName, String lastName, LocalDate dateOfBirth,
                 String gender, String phoneNumber, String email, String address) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        this.nurseId = HelperUtils.generateId("NuR");
        this.shift = "Morning"; // default
    }

    public Nurse(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String phoneNumber, String email, String address, String departmentId, String shift, String qualification, List<Patient> assignedPatients) {
        super(id, firstName, lastName, dateOfBirth, gender, phoneNumber, email, address);
        this.nurseId = HelperUtils.isNull(id) ? HelperUtils.generateId("NR") : id;
        this.departmentId = HelperUtils.isValidString(departmentId) ? departmentId : "";
        setShift(shift); // validates shift
        this.qualification = HelperUtils.isValidString(qualification) ? qualification : "";
        this.assignedPatients = assignedPatients;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = HelperUtils.isNull(nurseId) ? HelperUtils.generateId("NuR") : nurseId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = HelperUtils.isValidString(departmentId) ? departmentId : "";
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        //(Morning/Evening/Night)
        if(shift != null) {
            String normalizedShift = shift.toLowerCase();
            if (normalizedShift.equals("morning") || normalizedShift.equals("evening") || normalizedShift.equals("night")) {
                this.shift = normalizedShift;
            }
            else {
                System.out.println("Warning: Invalid shift '" + shift + "'. Expected 'Morning', 'Evening', or 'Night'. Setting to 'Morning' by default.");
                this.shift = "Morning";
            }
        } else {
            this.shift = "Morning"; // default value
        }
        this.shift = shift;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = HelperUtils.isValidString(qualification) ? qualification : "";
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
        sb.append(super.displayInfo(""));
        sb.append(System.lineSeparator());
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

    //Add methods for patient assignment
    //• Additional: getNursesByDepartment(), getNursesByShift()
}

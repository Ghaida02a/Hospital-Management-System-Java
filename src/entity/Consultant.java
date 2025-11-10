package entity;

import Interface.Displayable;
import Utils.HelperUtils;

import java.util.List;

public class Consultant extends Doctor implements Displayable {
    private List<String> consultationTypes;
    private boolean onlineConsultationAvailable;
    private int consultationDuration; // in minutes

    public Consultant() {
        super();
    }

    public Consultant(List<String> consultationTypes, boolean onlineConsultationAvailable, int consultationDuration) {
        this.consultationTypes = consultationTypes;
        this.onlineConsultationAvailable = onlineConsultationAvailable;
        this.consultationDuration = consultationDuration;
    }

    public List<String> getConsultationTypes() {
        return consultationTypes;
    }

    public void setConsultationTypes(List<String> consultationTypes) {
        if (HelperUtils.isNull(consultationTypes) || consultationTypes.isEmpty()) {
            System.out.println("Consultation types cannot be null or empty.");
        } else {
            this.consultationTypes = consultationTypes;
        }
    }

    public boolean isOnlineConsultationAvailable() {
        return onlineConsultationAvailable;
    }

    public void setOnlineConsultationAvailable(boolean onlineConsultationAvailable) {
        if (HelperUtils.isNotNull(onlineConsultationAvailable)) {
            this.onlineConsultationAvailable = onlineConsultationAvailable;
        } else {
            System.out.println("Online consultation availability must be specified.");
        }
    }

    public int getConsultationDuration() {
        return consultationDuration;
    }

    public void setConsultationDuration(int consultationDuration) {
        if (HelperUtils.isPositive(consultationDuration)) {
            this.consultationDuration = consultationDuration;
        } else {
            System.out.println("Consultation duration must be a positive integer.");
        }
    }

    public void scheduleConsultation(String type) {
        if (HelperUtils.isValidString(type)) {
            consultationTypes.add(type);
            System.out.println("Scheduled consultation: " + type +
                    " (Duration: " + consultationDuration + " minutes)");
        } else {
            System.out.println("Invalid consultation type.");
        }
    }

    public void provideSecondOpinion(String caseDetails) {
        if (HelperUtils.isValidString(caseDetails)) {
            System.out.println("Consultant " + getFirstName() + " " + getLastName() +
                    " is providing a second opinion on: " + caseDetails);
        } else {
            System.out.println("Case details must be provided for a second opinion.");
        }
    }
    @Override
    public String displayInfo() {
        // Start with the base doctor information
        StringBuilder info = new StringBuilder(super.displayInfo());

        // Add consultant-specific details
        info.append(System.lineSeparator());
        info.append("DR Consultant ID: ").append(this.getDoctorId()).append(System.lineSeparator());
        info.append("Consultation Types: ").append(getConsultationTypes()).append(System.lineSeparator());
        info.append("Online Consultation Available: ").append(isOnlineConsultationAvailable()).append(System.lineSeparator());
        info.append("Consultation Duration: ").append(getConsultationDuration()).append(" minutes\n");

        return info.toString();
    }

    @Override
    public String displaySummary(String str) {
        return "Consultant{" + getId() + ": " + getFirstName() + " " + getLastName() + "}";
    }

}

package entity;
import Interface.Displayable;
import Utils.HelperUtils;

import java.util.List;

public class Consultant extends Doctor implements Displayable {
    private List<String> consultationTypes;
    private boolean onlineConsultationAvailable;
    private int consultationDuration; // in minutes

    public Consultant(){
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
        if(HelperUtils.isNull(consultationTypes) || consultationTypes.isEmpty()) {
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
        if(HelperUtils.isPositive(consultationDuration)) {
            this.consultationDuration = consultationDuration;
        } else {
            System.out.println("Consultation duration must be a positive integer.");
        }
    }

    @Override
    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo()).append(System.lineSeparator());
        sb.append("Consultation Types: ").append(consultationTypes).append(System.lineSeparator());
        sb.append("Online Consultation Available: ").append(onlineConsultationAvailable).append(System.lineSeparator());
        sb.append("Consultation Duration: ").append(consultationDuration).append(" minutes");
        String out = sb.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "Consultant{" + getId() + ": " + getFirstName() + " " + getLastName() + "}";
    }

}

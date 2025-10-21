package entity;
import Interface.Displayable;
import java.util.List;

public class Consultant extends Doctor implements Displayable {
    private List<String> consultationTypes;
    private boolean onlineConsultationAvailable;
    private int consultationDuration; // in minutes

    public Consultant(List<String> consultationTypes, boolean onlineConsultationAvailable, int consultationDuration) {
        this.consultationTypes = consultationTypes;
        this.onlineConsultationAvailable = onlineConsultationAvailable;
        this.consultationDuration = consultationDuration;
    }

    public List<String> getConsultationTypes() {
        return consultationTypes;
    }

    public void setConsultationTypes(List<String> consultationTypes) {
        this.consultationTypes = consultationTypes;
    }

    public boolean isOnlineConsultationAvailable() {
        return onlineConsultationAvailable;
    }

    public void setOnlineConsultationAvailable(boolean onlineConsultationAvailable) {
        this.onlineConsultationAvailable = onlineConsultationAvailable;
    }

    public int getConsultationDuration() {
        return consultationDuration;
    }

    public void setConsultationDuration(int consultationDuration) {
        this.consultationDuration = consultationDuration;
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

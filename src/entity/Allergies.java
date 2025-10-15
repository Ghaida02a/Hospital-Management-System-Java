package entity;

import java.time.LocalDate;

public class Allergies {
    private String allergyId;          // unique identifier for the allergy
    private String allergyName;        // e.g., "Peanuts", "Penicillin", "Dust"

    public Allergies(String allergyId, String allergyName, String allergyType, String reaction, String severity, LocalDate dateIdentified, String notes) {
        this.allergyId = allergyId;
        this.allergyName = allergyName;

    }

    public String getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(String allergyId) {
        this.allergyId = allergyId;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }
}

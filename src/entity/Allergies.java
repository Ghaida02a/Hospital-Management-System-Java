package entity;

public class Allergies {
    private String allergyId;          // unique identifier for the allergy
    private String allergyName;        // e.g., "Peanuts", "Penicillin", "Dust"
    private String allergyType;

    public Allergies(String allergyId, String allergyName, String allergyType) {
        this.allergyId = allergyId;
        this.allergyName = allergyName;
        this.allergyType = allergyType;
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

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    @Override
    public String toString() {
        return allergyName + " (ID: " + allergyId + ", Type: " + allergyType + ")";
    }
}

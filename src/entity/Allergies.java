package entity;

import Interface.Displayable;

public class Allergies implements Displayable {
    private String allergyId;
    private String allergyName;        // e.g., "Peanuts", "Penicillin", "Dust"

    public Allergies(String allergyId, String allergyName) {
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

    @Override
    public String toString() {
        return allergyName + " (Id: " + allergyId+ " Allergy: " + allergyName + ")";
    }

    @Override
    public String displayInfo(String str) {
        String info = toString();
        System.out.println(info);
        return info;
    }

    @Override
    public String displaySummary(String str) {
        return allergyName == null ? "" : allergyName;
    }
}

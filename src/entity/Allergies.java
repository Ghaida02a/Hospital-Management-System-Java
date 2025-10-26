package entity;

import Interface.Displayable;
import Utils.HelperUtils;

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
        if(HelperUtils.isNotNull(allergyId) && !allergyId.isEmpty()) {
            this.allergyId = allergyId;
        } else {
            System.out.println("Allergy ID cannot be empty.");
        }
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        if(HelperUtils.isNotNull(allergyName) && !allergyName.isEmpty()) {
            this.allergyName = allergyName;
        } else {
            System.out.println("Allergy name cannot be empty.");
        }
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

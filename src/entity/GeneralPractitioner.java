package entity;

import Interface.Displayable;

public class GeneralPractitioner extends Doctor implements Displayable {
    private boolean walkinAvailable;
    private boolean homeVisitAvailable;
    private boolean vaccinationCertified;

    public GeneralPractitioner() {
        super();
    }

    public GeneralPractitioner(boolean walkinAvailable, boolean homeVisitAvailable, boolean vaccinationCertified) {
        this.walkinAvailable = walkinAvailable;
        this.homeVisitAvailable = homeVisitAvailable;
        this.vaccinationCertified = vaccinationCertified;
    }

    public boolean isWalkinAvailable() {
        return walkinAvailable;
    }

    public void setWalkinAvailable(boolean walkinAvailable) {
        if (walkinAvailable) {
            this.walkinAvailable = walkinAvailable;
        } else {
            System.out.println("Walk-in availability must be specified.");
        }
    }

    public boolean isHomeVisitAvailable() {
        return homeVisitAvailable;
    }

    public void setHomeVisitAvailable(boolean homeVisitAvailable) {
        if (homeVisitAvailable) {
            this.homeVisitAvailable = homeVisitAvailable;
        } else {
            System.out.println("Home visit availability must be specified.");
        }
    }

    public boolean isVaccinationCertified() {
        return vaccinationCertified;
    }

    public void setVaccinationCertified(boolean vaccinationCertified) {
        if (vaccinationCertified) {
            this.vaccinationCertified = vaccinationCertified;
        } else {
            System.out.println("Vaccination certification must be specified.");
        }
    }

    // GP-specific methods
    public void scheduleHomeVisit(String patientId, String dateTime) {
        if (homeVisitAvailable) {
            System.out.println("Home visit scheduled for patient " + patientId +
                    " at " + dateTime + " with GP " + getFirstName() + " " + getLastName());
        } else {
            System.out.println("Home visits are not available for this GP.");
        }
    }

    public void administerVaccine(String patientId, String vaccineName) {
        if (vaccinationCertified) {
            System.out.println("Administered " + vaccineName + " vaccine to patient " + patientId +
                    " by GP " + getFirstName() + " " + getLastName());
        } else {
            System.out.println("This GP is not certified to administer vaccines.");
        }
    }

    @Override
    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.displayInfo());
        sb.append(System.lineSeparator());
        sb.append("Walk-in Available: ").append(walkinAvailable).append(System.lineSeparator());
        sb.append("Home Visit Available: ").append(homeVisitAvailable).append(System.lineSeparator());
        sb.append("Vaccination Certified: ").append(vaccinationCertified);
        String out = sb.toString();
        System.out.println(out);
        return out;
    }

    @Override
    public String displaySummary(String str) {
        return "GP{" + getId() + ": " + getFirstName() + " " + getLastName() + "}";
    }
}

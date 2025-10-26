package entity;

import Interface.Displayable;
import Utils.HelperUtils;

import java.util.Date;


public class Billing implements Displayable {
    private String billId;
    private String patientId;
    private double amount;
    private Date billingDate;
    private String paymentStatus; // e.g., "Paid", "Pending", "Overdue"

    public Billing(String billId, String patientId, double amount, Date billingDate, String paymentStatus) {
        this.billId = billId;
        this.patientId = patientId;
        this.amount = amount;
        this.billingDate = billingDate;
        this.paymentStatus = paymentStatus;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        HelperUtils.generateId("BILL ");
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(HelperUtils.isNotNull(amount) && HelperUtils.isPositive(amount)) {
            this.amount = amount;
        } else {
            System.out.println("Amount must be a positive number.");
        }
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        if (HelperUtils.isNotNull(billingDate)) {
            Date today = new Date();
            if (billingDate.after(today)) {
                System.out.println("Billing date cannot be in the future.");
            } else {
                this.billingDate = billingDate;
            }
        } else {
            System.out.println("Billing date cannot be null.");
        }
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        if (HelperUtils.isNotNull(paymentStatus)) {
            // Normalize and validate allowed statuses
            String status = paymentStatus.trim().toLowerCase();

            if (status.equals("paid") || status.equals("pending") || status.equals("overdue")) {
                this.paymentStatus = status.substring(0, 1).toUpperCase() + status.substring(1);
            } else {
                System.out.println("Invalid payment status. Allowed values: Paid, Pending, Overdue.");
            }
        } else {
            System.out.println("Payment status cannot be null or empty.");
        }
    }


    @Override
    public String displayInfo(String str) {
        return "Billing Information:\n" +
                "Bill ID: " + billId + "\n" +
                "Patient ID: " + patientId + "\n" +
                "Amount: $" + amount + "\n" +
                "Billing Date: " + billingDate + "\n" +
                "Payment Status: " + paymentStatus + "\n";
    }

    @Override
    public String displaySummary(String str) {
        return "Bill ID: " + billId +
                " | Patient ID: " + patientId +
                " | Amount: $" + amount +
                " | Status: " + paymentStatus;
    }
}

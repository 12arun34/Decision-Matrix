package com.example.decisionmatrix;

public class Decision {
    private int serialNo;
    private String decisionName;

    // Default constructor
    public Decision() {
    }

    // Parameterized constructor
    public Decision(int serialNo, String decisionName) {
        this.serialNo = serialNo;
        this.decisionName = decisionName;
    }

    // Getter and Setter for serialNo
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    // Getter and Setter for decisionName
    public String getDecisionName() {
        return decisionName;
    }

    public void setDecisionName(String decisionName) {
        this.decisionName = decisionName;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "Decision{" +
                "serialNo=" + serialNo +
                ", decisionName='" + decisionName + '\'' +
                '}';
    }
}


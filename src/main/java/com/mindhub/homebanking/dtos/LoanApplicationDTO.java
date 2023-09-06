package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private long loanId;
    private double amount;
    private int payments;
    private String toAccountNumber;

    public LoanApplicationDTO(long loanId, double amount, int payments, String toAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public long getLoanId() { return loanId; }

    public double getAmount() { return amount; }

    public int getPayments() { return payments; }

    public String getToAccountNumber() { return toAccountNumber; }
}
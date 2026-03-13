package Loans;

public interface Loan {

    String getName();
    int getTerm();
    double getPrice();

    String fullPriceToPay();
}
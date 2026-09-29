package ge.tbc.testautomation.database.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "id")
public class LoanTestData {
    private Long id;
    private double amount;
    private int termMonths;
    private double expectedMonthlyPayment;

    public LoanTestData(double amount, int termMonths, double expectedMonthlyPayment) {
        this.amount = amount;
        this.termMonths = termMonths;
        this.expectedMonthlyPayment = expectedMonthlyPayment;
    }
}
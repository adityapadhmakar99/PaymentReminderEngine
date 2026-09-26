package paymentremindersystem.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Emi {

    private final String id;
    private final LocalDate dueDate;
    private final BigDecimal amountDue;
    private BigDecimal amountPaid;

    public Emi(String id, LocalDate dueDate, BigDecimal amountDue) {
        if (id == null || id.trim().isEmpty() || dueDate == null || amountDue == null
                || amountDue.signum() <= 0) {
            throw new IllegalArgumentException("valid id, dueDate, and positive amountDue are required");
        }
        this.id = id;
        this.dueDate = dueDate;
        this.amountDue = amountDue;
        this.amountPaid = BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getOutstandingAmount() {
        return amountDue.subtract(amountPaid).max(BigDecimal.ZERO);
    }

    public boolean isSettled() {
        return amountPaid.compareTo(amountDue) >= 0;
    }

    public void recordPayment(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("payment must be positive");
        }
        amountPaid = amountPaid.add(amount).min(amountDue);
    }
}

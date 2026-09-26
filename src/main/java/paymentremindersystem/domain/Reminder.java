package paymentremindersystem.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Reminder {

    private final String customerId;
    private final String loanId;
    private final String emiId;
    private final String recipient;
    private final Channel channel;
    private final int daysOverdue;
    private final LocalDate dueDate;
    private final BigDecimal outstandingAmount;

    public Reminder(String customerId, String loanId, String emiId, String recipient, Channel channel,
            int daysOverdue, LocalDate dueDate, BigDecimal outstandingAmount) {
        this.customerId = customerId;
        this.loanId = loanId;
        this.emiId = emiId;
        this.recipient = recipient;
        this.channel = channel;
        this.daysOverdue = daysOverdue;
        this.dueDate = dueDate;
        this.outstandingAmount = outstandingAmount;
    }

    public String getEmiId() {
        return emiId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }

    public Channel getChannel() {
        return channel;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    @Override
    public String toString() {
        return "Reminder{customerId='" + customerId + "', loanId='" + loanId + "', emiId='" + emiId
                + "', recipient='" + recipient + "', channel=" + channel + ", daysOverdue=" + daysOverdue
                + ", dueDate=" + dueDate + ", outstandingAmount=" + outstandingAmount + "}";
    }
}

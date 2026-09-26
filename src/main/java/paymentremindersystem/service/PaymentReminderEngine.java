package paymentremindersystem.service;

import paymentremindersystem.domain.*;
import paymentremindersystem.notification.ReminderSender;
import paymentremindersystem.repository.ReminderHistory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;



public final class PaymentReminderEngine {

    private final ReminderPolicy policy;
    private final ReminderSender sender;
    private final ReminderHistory history;

    public PaymentReminderEngine(ReminderPolicy policy, ReminderSender sender, ReminderHistory history) {
        if (policy == null || sender == null || history == null) {
            throw new IllegalArgumentException("policy, sender, and history are required");
        }
        this.policy = policy;
        this.sender = sender;
        this.history = history;
    }

    public void process(Collection<Customer> customers, LocalDate today) {
        if (customers == null || today == null) {
            throw new IllegalArgumentException("customers and today are required");
        }
        for (Customer customer : customers) {
            for (Loan loan : customer.getLoans()) {
                for (Emi emi : loan.getEmis()) {
                    sendIfDue(customer, loan, emi, today);
                }
            }
        }
    }

    private void sendIfDue(Customer customer, Loan loan, Emi emi, LocalDate today) {
        if (emi.isSettled() || !today.isAfter(emi.getDueDate())) {
            return;
        }

        int daysOverdue = (int) ChronoUnit.DAYS.between(emi.getDueDate(), today);
        ReminderStep step = policy.getStepFor(daysOverdue);
        if (step == null || history.hasBeenSent(emi.getId(), daysOverdue)) {
            return;
        }

        String recipient = customer.getContactFor(step.getChannel());
        if (recipient == null || recipient.trim().isEmpty()) {
            return;
        }

        Reminder reminder = new Reminder(customer.getId(), loan.getId(), emi.getId(), recipient,
                step.getChannel(), daysOverdue, emi.getDueDate(), emi.getOutstandingAmount());
        sender.send(reminder);
        history.recordSent(emi.getId(), daysOverdue);
    }
}

package paymentremindersystem;

import paymentremindersystem.domain.Customer;
import paymentremindersystem.domain.Emi;
import paymentremindersystem.domain.Loan;
import paymentremindersystem.domain.ReminderPolicy;
import paymentremindersystem.notification.ReminderSender;
import paymentremindersystem.repository.ReminderHistory;
import paymentremindersystem.repository.inmemory.InMemoryReminderHistory;
import paymentremindersystem.service.PaymentReminderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PaymentReminderEngine {

    static void main(String[] args) {

        // 1. Create EMI
        Emi emi = new Emi(
                "emi-1",
                LocalDate.now().minusDays(1),
                new BigDecimal("1250.00")
        );

        // 2. Create Loan
        Loan loan = new Loan(
                "loan-1",
                Collections.singletonList(emi)
        );

        // 3. Create Customer
        Customer customer = new Customer(
                "customer-1",
                "555-0100",
                "555-0100",
                Collections.singletonList(loan)
        );

        // 4. Create dependencies
        ReminderPolicy policy = ReminderPolicy.defaultPolicy();

        ReminderSender sender = reminder ->
                System.out.println(
                        "Reminder sent to " + reminder.getRecipient()
                                + " for EMI " + reminder.getEmiId()
                                + ", overdue by " + reminder.getDaysOverdue() + " days"
                );

        ReminderHistory history = new InMemoryReminderHistory();

        // 5. Create service
        PaymentReminderService service =
                new PaymentReminderService(
                        policy,
                        sender,
                        history
                );

        // 6. Process reminders
        service.process(
                List.of(customer),
                LocalDate.now()
        );
    }
}
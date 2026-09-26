package paymentremindersystem;

import paymentremindersystem.domain.Customer;
import paymentremindersystem.domain.Emi;
import paymentremindersystem.domain.Loan;
import paymentremindersystem.domain.ReminderPolicy;
import paymentremindersystem.repository.inmemory.InMemoryReminderHistory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

class PaymentReminderEngine {

    private final ReminderPolicy reminderPolicy;
    private final Consumer<String> reminderSender;
    private final InMemoryReminderHistory reminderHistory;

    public PaymentReminderEngine(
            ReminderPolicy reminderPolicy,
            Consumer<String> reminderSender,
            InMemoryReminderHistory reminderHistory) {

        this.reminderPolicy = reminderPolicy;
        this.reminderSender = reminderSender;
        this.reminderHistory = reminderHistory;
    }

    public void process(List<Customer> customers, LocalDate today) {

        for (Customer customer : customers) {
            for (Loan loan : customer.getLoans()) {
                for (Emi emi : loan.getEmis()) {

                    if (emi.getDueDate().isBefore(today)) {
                        reminderSender.accept(
                                "Reminder: Customer " + customer.getId()
                                        + " has an overdue EMI of "
                                        + emi.getOutstandingAmount()
                        );
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Emi emi = new Emi(
                "emi-1",
                LocalDate.now().minusDays(1),
                new BigDecimal("1250.00")
        );

        Loan loan = new Loan(
                "loan-1",
                Collections.singletonList(emi)
        );

        Customer customer = new Customer(
                "customer-1",
                "555-0100",
                "555-0100",
                Collections.singletonList(loan)
        );

        PaymentReminderEngine engine = new PaymentReminderEngine(
                ReminderPolicy.defaultPolicy(),
                System.out::println,
                new InMemoryReminderHistory()
        );

        engine.process(
                List.of(customer),
                LocalDate.now()
        );
    }
}
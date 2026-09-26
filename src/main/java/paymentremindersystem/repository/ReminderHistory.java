package paymentremindersystem.repository;

public interface ReminderHistory {

    boolean hasBeenSent(String emiId, int daysOverdue);

    void recordSent(String emiId, int daysOverdue);
}
